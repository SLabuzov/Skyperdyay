package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.Transaction;
import by.skyperdyay.engine.core.domain.repository.TransactionRepository;
import by.skyperdyay.engine.core.domain.service.TransactionDomainService;
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
}
