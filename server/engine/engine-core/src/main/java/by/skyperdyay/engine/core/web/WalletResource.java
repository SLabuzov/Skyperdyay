package by.skyperdyay.engine.core.web;

import by.skyperdyay.engine.core.middleware.model.request.RegisterWalletRequest;
import by.skyperdyay.engine.core.middleware.service.WalletEdgeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallets")
public class WalletResource {

    private final WalletEdgeService walletEdgeService;

    public WalletResource(WalletEdgeService walletEdgeService) {
        this.walletEdgeService = walletEdgeService;
    }

    @PostMapping
    void registerWallet(@RequestBody RegisterWalletRequest request) {
        walletEdgeService.registerWallet(request);
    }
}
