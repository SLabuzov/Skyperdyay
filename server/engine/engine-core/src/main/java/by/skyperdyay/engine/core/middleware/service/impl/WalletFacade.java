package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.engine.core.domain.model.Account;
import by.skyperdyay.engine.core.domain.model.Wallet;
import by.skyperdyay.engine.core.domain.service.AccountDomainService;
import by.skyperdyay.engine.core.domain.service.WalletDomainService;
import by.skyperdyay.engine.core.middleware.model.request.RegisterWalletRequest;
import by.skyperdyay.engine.core.middleware.service.WalletEdgeService;
import by.skyperdyay.security.api.CurrentUserApiService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WalletFacade implements WalletEdgeService {

    private final CurrentUserApiService currentUserApiService;
    private final AccountDomainService accountDomainService;
    private final WalletDomainService walletDomainService;

    public WalletFacade(CurrentUserApiService currentUserApiService,
                        AccountDomainService accountDomainService,
                        WalletDomainService walletDomainService) {
        this.currentUserApiService = currentUserApiService;
        this.accountDomainService = accountDomainService;
        this.walletDomainService = walletDomainService;
    }

    @Override
    public void registerWallet(RegisterWalletRequest request) {
        String owner = currentUserApiService.currentUserAccount().userId();

        Account linkedToWalletAccount = accountDomainService.registerWalletTypeAccount(owner, request.balance());

        Wallet wallet = new Wallet();
        wallet.setName(request.walletName());
        wallet.setOwner(owner);
        wallet.setLinkedAccount(linkedToWalletAccount);
        walletDomainService.registerWallet(wallet);
    }
}
