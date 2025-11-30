package by.skyperdyay.engine.analytics.middleware.validator.impl;

import by.skyperdyay.engine.analytics.middleware.validator.FinancialDashboardValidator;
import by.skyperdyay.engine.core.api.WalletApiService;
import org.springframework.stereotype.Service;

@Service
public class FinancialDashboardValidatorImpl implements FinancialDashboardValidator {

    private final WalletApiService walletApiService;

    public FinancialDashboardValidatorImpl(WalletApiService walletApiService) {
        this.walletApiService = walletApiService;
    }

    @Override
    public void validateBeforeExecution(String owner) {
        validateOwnerWalletExists(owner);
    }

    private void validateOwnerWalletExists(String owner) {
        if (walletApiService.countOwnerWallets(owner) == 0) {
            throw new RuntimeException("Создайте кошелек");
        }
    }
}
