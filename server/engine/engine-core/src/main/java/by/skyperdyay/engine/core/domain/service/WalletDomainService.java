package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Currency;
import by.skyperdyay.engine.core.domain.model.Wallet;
import java.util.List;

public interface WalletDomainService {
    void ensureUserWalletUniqueness(String owner, Currency currency);

    void registerWallet(Wallet wallet);

    List<Wallet> fetchAllUserWallets(String owner);
}
