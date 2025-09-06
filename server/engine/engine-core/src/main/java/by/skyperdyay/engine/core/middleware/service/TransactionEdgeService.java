package by.skyperdyay.engine.core.middleware.service;

import by.skyperdyay.engine.core.middleware.model.request.ExpenseTransactionRequest;
import by.skyperdyay.engine.core.middleware.model.request.IncomeTransactionRequest;

public interface TransactionEdgeService {
    void recordIncome(IncomeTransactionRequest request);

    void recordExpense(ExpenseTransactionRequest request);
}
