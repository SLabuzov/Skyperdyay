package by.skyperdyay.engine.core.middleware.model.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record ExpenseTransactionRequest(
        UUID walletId,
        UUID expenseCategoryId,
        BigDecimal amount,
        Date transactionDate,
        String notes
) {
}
