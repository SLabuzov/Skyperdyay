package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Transfer;

public interface TransferDomainService {
    Transfer recordTransferTransaction(Transfer transfer);
}
