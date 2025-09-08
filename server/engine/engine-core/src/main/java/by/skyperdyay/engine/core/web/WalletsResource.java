package by.skyperdyay.engine.core.web;

import by.skyperdyay.engine.core.middleware.model.request.RegisterWalletRequest;
import by.skyperdyay.engine.core.middleware.model.response.WalletInfoResponse;
import by.skyperdyay.engine.core.middleware.service.WalletEdgeService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallets")
public class WalletsResource {

    private final WalletEdgeService walletEdgeService;

    public WalletsResource(WalletEdgeService walletEdgeService) {
        this.walletEdgeService = walletEdgeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void registerWallet(@RequestBody RegisterWalletRequest request) {
        walletEdgeService.registerWallet(request);
    }

    @GetMapping
    List<WalletInfoResponse> availableWallets() {
        return walletEdgeService.availableWallets();
    }

    @GetMapping("/{walletId}")
    WalletInfoResponse showWalletInfo(@PathVariable("walletId") UUID walletId) {
        return walletEdgeService.availableWallets().stream()
                .filter(wallet -> wallet.walletId().equals(walletId))
                .findFirst()
                .orElseThrow();
    }
}
