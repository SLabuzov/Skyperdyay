package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.Transaction;
import by.skyperdyay.engine.core.domain.repository.TransactionRepository;
import by.skyperdyay.engine.core.domain.service.TransactionDomainService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionDomainServiceImpl implements TransactionDomainService {

    private final TransactionRepository transactionRepository;

    public TransactionDomainServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void recordTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> extractOwnerTransactionsByPeriod(String owner, LocalDate startPeriod, LocalDate endPeriod) {
        return transactionRepository.findAllByOwnerAndTransactionDateBetweenOrderByTransactionDateDesc(owner, startPeriod, endPeriod);
    }
}
