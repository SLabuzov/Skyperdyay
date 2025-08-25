package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.Currency;
import by.skyperdyay.engine.core.domain.model.Wallet;
import by.skyperdyay.engine.core.domain.repository.WalletRepository;
import by.skyperdyay.engine.core.domain.service.WalletDomainService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WalletDomainServiceImpl implements WalletDomainService {

    private final WalletRepository walletRepository;

    public WalletDomainServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void ensureUserWalletUniqueness(String owner, Currency currency) {
        if (walletRepository.existsByOwnerAndCurrency(owner, currency)) {
            throw new RuntimeException();
        }
    }

    @Override
    public void registerWallet(Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Override
    public List<Wallet> fetchAllUserWallets(String owner) {
        return walletRepository.findAllByOwner(owner);
    }
}
