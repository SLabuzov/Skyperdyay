package by.skyperdyay.engine.core.middleware.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseTransactionRequest(
        UUID walletId,
        UUID expenseCategoryId,
        BigDecimal amount,
        LocalDate transactionDate,
        String notes
) {
}
