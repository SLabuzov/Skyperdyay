package by.skyperdyay.engine.core.middleware.service;

import by.skyperdyay.engine.core.middleware.model.request.TransferRequest;

public interface TransferEdgeService {
    void executeFundsTransfer(TransferRequest request);
}
