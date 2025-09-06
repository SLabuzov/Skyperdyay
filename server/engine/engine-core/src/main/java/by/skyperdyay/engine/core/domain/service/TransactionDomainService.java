package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Transaction;

public interface TransactionDomainService {
    void recordTransaction(Transaction transaction);
}
