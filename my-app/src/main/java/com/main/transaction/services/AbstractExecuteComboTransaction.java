package com.main.transaction.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.main.entity.SecureSubTransactionEntity;
import com.main.entity.SecureTransactionEntity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class ISubTransactionInput<T> {
    public String logCorrelationId;
    public String cimbTransactionId;
    public T data;
}

class IExecuteTransactionContext {
    SecureTransactionEntity secureTransaction;
    SecureSubTransactionEntity secureSubTransaction;
}

public abstract class AbstractExecuteComboTransaction<InputSubTransaction, OutputSubTransaction>
      extends BaseSubTransactionConfiguration implements ExecuteSubTransaction<InputSubTransaction, OutputSubTransaction> {
    private InputSubTransaction inputDTOclass;

    public abstract OutputSubTransaction handler(ISubTransactionInput<InputSubTransaction> input,
            IExecuteTransactionContext context);

    @Override
    public Optional<InputSubTransaction> tranformAndValidateInput(Optional<InputSubTransaction> input) {
        if (this.inputDTOclass == null || input.isEmpty()) {
            return null;
        }
        Gson gson = new Gson();
        String json = gson.toJson(input);
        var yourObject = gson.fromJson(json, this.inputDTOclass.getClass());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        var violations = validator.validate(yourObject);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> violation : violations) {
                System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input is not valid");
        } 

        factory.close();
        return  (Optional<InputSubTransaction>) yourObject;

    }
}
