package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.api.WalletApiService;
import by.skyperdyay.engine.core.domain.model.Currency;
import by.skyperdyay.engine.core.domain.model.Wallet;
import by.skyperdyay.engine.core.domain.repository.WalletRepository;
import by.skyperdyay.engine.core.domain.service.WalletDomainService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class WalletDomainServiceImpl implements WalletDomainService, WalletApiService {

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

    @Override
    public Wallet fetchUserWallet(UUID id, String owner) {
        return walletRepository.findByIdAndOwner(id, owner)
                .orElseThrow();
    }

    @Override
    public void topUpWallet(Wallet wallet, BigDecimal amount) {
        BigDecimal updatedBalance = wallet.getBalance().add(amount);
        wallet.setBalance(updatedBalance);

        walletRepository.save(wallet);
    }

    @Override
    public void withdraw(Wallet wallet, BigDecimal amount) {
        BigDecimal updatedBalance = wallet.getBalance().subtract(amount);
        wallet.setBalance(updatedBalance);

        walletRepository.save(wallet);
    }

    @Override
    public int countOwnerWallets(String owner) {
        return walletRepository.countByOwner(owner);
    }
}
