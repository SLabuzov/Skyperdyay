package by.skyperdyay.engine.core.middleware.model.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionInfoResponse(
        UUID transactionId,
        BigDecimal amount,
        LocalDate transactionDate,
        String notes,
        WalletInfoResponse wallet,
        CategoryResponse category
) {
}
