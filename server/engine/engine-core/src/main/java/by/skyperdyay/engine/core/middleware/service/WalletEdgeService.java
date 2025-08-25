package by.skyperdyay.engine.core.middleware.service;

import by.skyperdyay.engine.core.middleware.model.request.RegisterWalletRequest;
import by.skyperdyay.engine.core.middleware.model.response.WalletInfoResponse;
import java.util.List;

public interface WalletEdgeService {

    void registerWallet(RegisterWalletRequest request);

    List<WalletInfoResponse> availableWallets();
}
