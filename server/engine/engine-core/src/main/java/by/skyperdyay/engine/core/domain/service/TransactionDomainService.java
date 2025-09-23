package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Transaction;
import java.time.LocalDate;
import java.util.List;

public interface TransactionDomainService {
    void recordTransaction(Transaction transaction);

    List<Transaction> extractOwnerTransactionsByPeriod(String owner, LocalDate startPeriod, LocalDate endPeriod);
}
