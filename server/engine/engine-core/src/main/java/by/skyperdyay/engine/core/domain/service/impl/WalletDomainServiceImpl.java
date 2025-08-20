package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.Wallet;
import by.skyperdyay.engine.core.domain.repository.WalletRepository;
import by.skyperdyay.engine.core.domain.service.WalletDomainService;
import org.springframework.stereotype.Service;

@Service
public class WalletDomainServiceImpl implements WalletDomainService {

    private final WalletRepository walletRepository;

    public WalletDomainServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void registerWallet(Wallet wallet) {
        walletRepository.save(wallet);
    }
}
