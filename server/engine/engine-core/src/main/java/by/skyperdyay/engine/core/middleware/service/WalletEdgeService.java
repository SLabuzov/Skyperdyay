package by.skyperdyay.engine.core.middleware.service;

import by.skyperdyay.engine.core.middleware.model.request.RegisterWalletRequest;

public interface WalletEdgeService {
    void registerWallet(RegisterWalletRequest request);
}
