package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.engine.core.domain.model.Currency;
import by.skyperdyay.engine.core.domain.model.Wallet;
import by.skyperdyay.engine.core.domain.service.CurrencyDomainService;
import by.skyperdyay.engine.core.domain.service.WalletDomainService;
import by.skyperdyay.engine.core.middleware.mapper.WalletMapper;
import by.skyperdyay.engine.core.middleware.model.request.RegisterWalletRequest;
import by.skyperdyay.engine.core.middleware.model.response.WalletInfoResponse;
import by.skyperdyay.engine.core.middleware.service.WalletEdgeService;
import by.skyperdyay.security.api.CurrentUserApiService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WalletFacade implements WalletEdgeService {

    private final CurrentUserApiService currentUserApiService;
    private final WalletDomainService walletDomainService;
    private final CurrencyDomainService currencyDomainService;
    private final WalletMapper walletMapper;

    public WalletFacade(CurrentUserApiService currentUserApiService,
                        WalletDomainService walletDomainService,
                        CurrencyDomainService currencyDomainService,
                        WalletMapper walletMapper) {
        this.currentUserApiService = currentUserApiService;
        this.walletDomainService = walletDomainService;
        this.currencyDomainService = currencyDomainService;
        this.walletMapper = walletMapper;
    }

    @Override
    public void registerWallet(RegisterWalletRequest request) {
        String owner = currentUserApiService.currentUserAccount().userId();
        Currency currency = currencyDomainService.fetchCurrency(request.currencyCode());

        walletDomainService.ensureUserWalletUniqueness(owner, currency);

        Wallet wallet = new Wallet();
        wallet.setName(request.walletName());
        wallet.setOwner(owner);
        wallet.setBalance(request.balance());
        wallet.setCurrency(currency);
        wallet.setActive(true);

        walletDomainService.registerWallet(wallet);
    }

    @Override
    public List<WalletInfoResponse> availableWallets() {
        String owner = currentUserApiService.currentUserAccount().userId();

        return walletDomainService.fetchAllUserWallets(owner)
                .stream()
                .map(walletMapper::convert)
                .toList();
    }
}
