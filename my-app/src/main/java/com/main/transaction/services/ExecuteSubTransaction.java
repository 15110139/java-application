package com.main.transaction.services;

import java.util.Optional;


public interface ExecuteSubTransaction<InputSubTransaction, OutputSubTransaction> {
    public OutputSubTransaction execute(SubTransactionInput<InputSubTransaction> input, ExecuteSubTransactionContext context);
    public Optional<InputSubTransaction> tranformAndValidateInput(Optional<InputSubTransaction> input);
}