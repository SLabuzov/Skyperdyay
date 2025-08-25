package by.skyperdyay.engine.core.middleware.model.response;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletInfoResponse(
        UUID walletId,
        String walletName,
        BigDecimal walletBalance,
        CurrencyResponse walletCurrency
) {
}
