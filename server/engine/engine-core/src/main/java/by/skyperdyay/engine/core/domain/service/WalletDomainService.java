package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Currency;
import by.skyperdyay.engine.core.domain.model.Wallet;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface WalletDomainService {
    void ensureUserWalletUniqueness(String owner, Currency currency);

    void registerWallet(Wallet wallet);

    List<Wallet> fetchAllUserWallets(String owner);

    Wallet fetchUserWallet(UUID id, String owner);

    void topUpWallet(Wallet wallet, BigDecimal amount);

    void withdraw(Wallet wallet, BigDecimal amount);
}
