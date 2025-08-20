package by.skyperdyay.engine.core.middleware.model.request;

import java.math.BigDecimal;

public record RegisterWalletRequest(
        String walletName,
        BigDecimal balance
) {
}
