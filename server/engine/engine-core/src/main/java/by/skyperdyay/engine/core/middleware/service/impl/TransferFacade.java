package by.skyperdyay.engine.core.middleware.service.impl;

import by.skyperdyay.common.exception.api.InsufficientFundsException;
import by.skyperdyay.common.exception.api.InvalidTransferException;
import by.skyperdyay.engine.core.domain.model.Transfer;
import by.skyperdyay.engine.core.domain.model.Wallet;
import by.skyperdyay.engine.core.domain.service.TransferDomainService;
import by.skyperdyay.engine.core.domain.service.WalletDomainService;
import by.skyperdyay.engine.core.middleware.model.request.TransferRequest;
import by.skyperdyay.engine.core.middleware.service.TransferEdgeService;
import by.skyperdyay.security.api.CurrentUserApiService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransferFacade implements TransferEdgeService {

    private final CurrentUserApiService currentUserApiService;
    private final WalletDomainService walletDomainService;
    private final TransferDomainService transferDomainService;

    public TransferFacade(CurrentUserApiService currentUserApiService,
                          WalletDomainService walletDomainService,
                          TransferDomainService transferDomainService) {
        this.currentUserApiService = currentUserApiService;
        this.walletDomainService = walletDomainService;
        this.transferDomainService = transferDomainService;
    }

    @Override
    public void executeFundsTransfer(TransferRequest request) {
        // Бизнес-валидация: проверяем, что исходный и целевой кошельки разные
        validateDifferentWallets(request);

        String owner = currentUserApiService.currentUserAccount().userId();

        // Получаем кошельки
        Wallet sourceWallet = walletDomainService.fetchUserWallet(request.sourceWalletId(), owner);
        Wallet targetWallet = walletDomainService.fetchUserWallet(request.targetWalletId(), owner);

        // Бизнес-валидация: проверяем достаточно ли средств для уменьшения баланса
        validateSufficientFunds(sourceWallet, request.sourceAmount());

        // Сохраняем запись о переводе
        Transfer transfer = new Transfer();
        transfer.setOwner(owner);
        transfer.setSourceWallet(sourceWallet);
        transfer.setTargetWallet(targetWallet);
        transfer.setSourceAmount(request.sourceAmount());
        transfer.setTargetAmount(request.targetAmount());
        transfer.setTransferDate(request.transferDate());
        transfer.setNotes(request.notes());
        Transfer savedTransfer = transferDomainService.recordTransferTransaction(transfer);

        // Выполняем операции по переводу
        walletDomainService.withdraw(sourceWallet, savedTransfer.getSourceAmount());
        walletDomainService.topUpWallet(targetWallet, savedTransfer.getTargetAmount());
    }

    private void validateDifferentWallets(TransferRequest request) {
        if (request.sourceWalletId().equals(request.targetWalletId())) {
            throw new InvalidTransferException(
                    "Недействительный перевод",
                    "Исходный и целевой кошельки должны быть разными"
            );
        }
    }

    private void validateSufficientFunds(Wallet wallet, BigDecimal amount) {
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                    "Недостаточно средств",
                    String.format("Недостаточно средств. Доступно: %s, Требуется: %s",
                            wallet.getBalance(), amount));
        }
    }
}
