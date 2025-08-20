package by.skyperdyay.engine.core.domain.service;

import by.skyperdyay.engine.core.domain.model.Account;
import java.math.BigDecimal;

public interface AccountDomainService {

    Account registerWalletTypeAccount(String owner, BigDecimal initialBalance);
}
