package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.Transfer;
import by.skyperdyay.engine.core.domain.repository.TransferRepository;
import by.skyperdyay.engine.core.domain.service.TransferDomainService;
import org.springframework.stereotype.Service;

@Service
public class TransferDomainServiceImpl implements TransferDomainService {

    private final TransferRepository transferRepository;

    public TransferDomainServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public Transfer recordTransferTransaction(Transfer transfer) {
        return transferRepository.save(transfer);
    }
}
