package com.main.transaction.enums;

public enum SubTransactionPurpose {
    TREE_DS("3DS"),
    CARD_AUTH,
    CIMB_INTERNAL_ACCT,
    CARD_SETTLE ;

    private final String value;

    // Constructor for custom value
    SubTransactionPurpose(String value) {
        this.value = value;
    }

    // Default constructor, uses enum name as value
    SubTransactionPurpose() {
        this.value = name();
    }

    public String getValue() {
        return value;
    }
}

