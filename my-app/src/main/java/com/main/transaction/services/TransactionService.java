package com.main.transaction.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.main.entity.SecureSubTransactionEntity;
import com.main.repository.SecureSubTransactionRepository;
import com.main.repository.specitifications.SecureSubTransactionSpecifications;
import com.main.transaction.enums.SubTransactionType;
import com.main.transaction.enums.TransactionStatus;
import com.main.transaction.enums.TransactionType;
import com.main.transaction.services.BaseSubTransactionConfiguration.PreCondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
public class TransactionService {

    protected static final Logger logger = LogManager.getLogger();

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class InputMappingConfig {
        public String source;

        public String property;

        public String requestId;

        public String key;
    }

    @Autowired
    private SecureSubTransactionRepository secureSubTransactionRepo;

    public void checkPreCondition(String correlationId, String secureTransactionId,
            AbstractExecuteComboTransaction<?, ?> subTransactionConfiguration) {
        logger.info(correlationId + " Start check pre condition of " + subTransactionConfiguration.getName());
        for (Entry<SubTransactionType, PreCondition> entry : subTransactionConfiguration.getPreCondition()
                .entrySet()) {
            SecureSubTransactionSpecifications findWithTransactionIdAndStatus = new SecureSubTransactionSpecifications();
            findWithTransactionIdAndStatus.withTransactionId(secureTransactionId).withStatusIn(entry.getValue().status);
            Optional<SecureSubTransactionEntity> secureSubTransaction = secureSubTransactionRepo
                    .findOne(findWithTransactionIdAndStatus.build());
            if (secureSubTransaction.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fucking secure transaction");
            }
        }

    }

    public Boolean isCanExecuteSubTransaction(String corrrelationId, SecureSubTransactionEntity subTransaction,
            AbstractExecuteComboTransaction<?, ?> step) {
        if (subTransaction.getStatus() == TransactionStatus.COMPLETED || subTransaction.getStatus() == null) {
            return false;
        }

        if (subTransaction.getStatus() == TransactionStatus.INIT
                || step.executeStatus.contains(subTransaction.getStatus())) {
            return true;
        }
        return false;
    }

    public Optional<HashMap<String, String>> inputMapping(
            String correlationId, String secureTransactionId,
            AbstractExecuteComboTransaction<?, ?> step) {
        var newInput = new HashMap<String, String>();
        ArrayList<String> sources = new ArrayList<>();
        ArrayList<InputMappingConfig> inputMappingConfigs = new ArrayList<>();
        for (var entry : step.getInputMapping().entrySet()) {
            var property = entry.getValue().property;
            var source = entry.getValue().source.toString();
            var requestId = entry.getValue().requestId;
            var key = entry.getKey();
            inputMappingConfigs.add(new InputMappingConfig(source, property, requestId, key));
        }

        var sourceFormSubTransaction = sources.stream().filter(source -> source != "INPUT")
                .map(TransactionType::valueOf).collect(Collectors.toList());
        SecureSubTransactionSpecifications findWithSecureTransactionIdAndSubTranactionType = new SecureSubTransactionSpecifications();
        findWithSecureTransactionIdAndSubTranactionType.withTransactionId(secureTransactionId)
                .withTypeIn(sourceFormSubTransaction);
        var subTransactions = secureSubTransactionRepo.findAll(findWithSecureTransactionIdAndSubTranactionType.build());

        inputMappingConfigs.forEach(inputMappingConfig -> {
            if (inputMappingConfig.getSource().equals("INPUT")) {
                if (step.input.isPresent()) {
                    var inputMap = step.input.get();
                    var value = inputMap.get(inputMappingConfig.getKey());
                    if (value.isEmpty()) {
                        logger.error("No property in input map with input config");
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO_DATA_FOR_INPUT_MAPPING");
                    }
                    newInput.put(inputMappingConfig.getKey(), value);
                } else {
                    logger.error("No input data available");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO_DATA_FOR_INPUT_MAPPING");
                }
            } else {
                var subTransactionsFitler = subTransactions.stream()
                        .filter(sub -> sub.getType().toString().equals(inputMappingConfig.source)
                                && sub.getRequestId().equals(inputMappingConfig.requestId))
                        .collect(Collectors.toList());
                if (subTransactionsFitler.size() != 1) {
                    logger.error("Not found input mapping with " + inputMappingConfig.getSource() + "- "
                            + inputMappingConfig.getProperty());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO_DATA_FOUND_FOR_INPUT_MAPPING");
                }

                var payload = subTransactionsFitler.get(0).getClass();
                try {
                    var field = payload.getDeclaredField(inputMappingConfig.getProperty());
                    field.setAccessible(true);
                    newInput.put(inputMappingConfig.getKey(), field.toString());
                } catch (NoSuchFieldException | SecurityException e) {
                    logger.info("Not found value mapping in " + inputMappingConfig.getProperty());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO_DATA_FOUND_FOR_INPUT_MAPPING");
                }
            }
        });

        if(step.getInput().isPresent()){
             newInput.putAll(step.getInput().get());
             return Optional.ofNullable(newInput);
        }
        
        return Optional.ofNullable(newInput);
    }
}
