package by.skyperdyay.engine.core.web;

import by.skyperdyay.engine.core.middleware.model.request.ExpenseTransactionRequest;
import by.skyperdyay.engine.core.middleware.model.request.IncomeTransactionRequest;
import by.skyperdyay.engine.core.middleware.service.TransactionEdgeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsResource {

    private final TransactionEdgeService transactionEdgeService;

    public TransactionsResource(TransactionEdgeService transactionEdgeService) {
        this.transactionEdgeService = transactionEdgeService;
    }

    @PostMapping("/income")
    @ResponseStatus(HttpStatus.CREATED)
    void recordIncome(@RequestBody IncomeTransactionRequest request) {
        transactionEdgeService.recordIncome(request);
    }

    @PostMapping("/expense")
    @ResponseStatus(HttpStatus.CREATED)
    void recordExpense(@RequestBody ExpenseTransactionRequest request) {
        transactionEdgeService.recordExpense(request);
    }
}
