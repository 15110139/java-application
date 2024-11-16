package com.main.transaction.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.main.transaction.enums.SubTransactionType;
import com.main.transaction.enums.TransactionStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseSubTransactionConfiguration {
    class PreCondition {
        public String requestId;
    
        public ArrayList<TransactionStatus> status;
    
        public SubTransactionType name;
    }

    public class InputMapping {
        public String requestId;
    
        public String property;
    
        public SubTransactionType source;
    }

    public HashMap<SubTransactionType,PreCondition> preCondition;
    public HashMap<String,InputMapping>  inputMapping;
    public String name;
    public ArrayList<TransactionStatus> executeStatus;
    public Optional<HashMap<String,String>> input; 
}
