package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.engine.core.domain.model.Category;
import by.skyperdyay.engine.core.domain.model.Transaction;
import by.skyperdyay.engine.core.domain.model.Wallet;
import by.skyperdyay.engine.core.domain.service.CategoryDomainService;
import by.skyperdyay.engine.core.domain.service.TransactionDomainService;
import by.skyperdyay.engine.core.domain.service.WalletDomainService;
import by.skyperdyay.engine.core.middleware.mapper.TransactionMapper;
import by.skyperdyay.engine.core.middleware.model.request.ExpenseTransactionRequest;
import by.skyperdyay.engine.core.middleware.model.request.IncomeTransactionRequest;
import by.skyperdyay.engine.core.middleware.model.request.PeriodRequest;
import by.skyperdyay.engine.core.middleware.model.response.TransactionInfoResponse;
import by.skyperdyay.engine.core.middleware.service.TransactionEdgeService;
import by.skyperdyay.security.api.CurrentUserApiService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionFacade implements TransactionEdgeService {

    private final CurrentUserApiService currentUserApiService;
    private final CategoryDomainService categoryDomainService;
    private final WalletDomainService walletDomainService;
    private final TransactionDomainService transactionDomainService;
    private final TransactionMapper transactionMapper;

    public TransactionFacade(CurrentUserApiService currentUserApiService,
                             CategoryDomainService categoryDomainService,
                             WalletDomainService walletDomainService,
                             TransactionDomainService transactionDomainService,
                             TransactionMapper transactionMapper) {
        this.currentUserApiService = currentUserApiService;
        this.categoryDomainService = categoryDomainService;
        this.walletDomainService = walletDomainService;
        this.transactionDomainService = transactionDomainService;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public void recordIncome(IncomeTransactionRequest request) {
        String owner = currentUserApiService.currentUserAccount().userId();
        Wallet wallet = walletDomainService.fetchUserWallet(request.walletId(), owner);
        Category category = categoryDomainService.fetchIncomeUserCategory(request.incomeCategoryId(), owner);

        Transaction transaction = new Transaction();
        transaction.setOwner(owner);
        transaction.setWallet(wallet);
        transaction.setCategory(category);
        transaction.setAmount(request.amount());
        transaction.setNotes(request.notes());
        transaction.setTransactionDate(request.transactionDate());
        transactionDomainService.recordTransaction(transaction);

        walletDomainService.topUpWallet(wallet, request.amount());
    }

    @Override
    public void recordExpense(ExpenseTransactionRequest request) {
        String owner = currentUserApiService.currentUserAccount().userId();
        Wallet wallet = walletDomainService.fetchUserWallet(request.walletId(), owner);
        Category category = categoryDomainService.fetchExpenseUserCategory(request.expenseCategoryId(), owner);

        // пробуем снять сумму больше, чем имеем в кошельке
        if (wallet.getBalance().compareTo(request.amount()) < 0) {
            throw new RuntimeException();
        }

        Transaction transaction = new Transaction();
        transaction.setOwner(owner);
        transaction.setWallet(wallet);
        transaction.setCategory(category);
        transaction.setAmount(request.amount());
        transaction.setNotes(request.notes());
        transaction.setTransactionDate(request.transactionDate());
        transactionDomainService.recordTransaction(transaction);

        walletDomainService.withdraw(wallet, request.amount());
    }

    @Override
    public List<TransactionInfoResponse> extractOperationsByPeriod(PeriodRequest request) {
        String owner = currentUserApiService.currentUserAccount().userId();

        List<Transaction> transactions = transactionDomainService
                .extractOwnerTransactionsByPeriod(owner, request.startPeriod(), request.endPeriod());

        return transactions.stream()
                .map(transactionMapper::convert)
                .toList();
    }
}
