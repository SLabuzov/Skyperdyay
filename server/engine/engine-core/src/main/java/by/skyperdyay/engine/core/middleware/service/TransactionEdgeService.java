package by.skyperdyay.engine.core.middleware.service;

import by.skyperdyay.engine.core.middleware.model.request.ExpenseTransactionRequest;
import by.skyperdyay.engine.core.middleware.model.request.IncomeTransactionRequest;
import by.skyperdyay.engine.core.middleware.model.request.PeriodRequest;
import by.skyperdyay.engine.core.middleware.model.response.TransactionInfoResponse;
import java.util.List;

public interface TransactionEdgeService {
    void recordIncome(IncomeTransactionRequest request);

    void recordExpense(ExpenseTransactionRequest request);

    List<TransactionInfoResponse> extractOperationsByPeriod(PeriodRequest request);
}
