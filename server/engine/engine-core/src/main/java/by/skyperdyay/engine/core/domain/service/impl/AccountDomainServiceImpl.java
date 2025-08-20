package by.skyperdyay.engine.core.domain.service.impl;

import by.skyperdyay.engine.core.domain.model.Account;
import by.skyperdyay.engine.core.domain.model.AccountType;
import by.skyperdyay.engine.core.domain.repository.AccountRepository;
import by.skyperdyay.engine.core.domain.service.AccountDomainService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class AccountDomainServiceImpl implements AccountDomainService {

    private final AccountRepository accountRepository;

    public AccountDomainServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account registerWalletTypeAccount(String owner, BigDecimal initialBalance) {
        Account account = new Account();
        account.setActive(true);
        account.setType(AccountType.WALLET);
        account.setOwner(owner);
        account.setBalance(initialBalance);

        return registerFinancialAccount(account);
    }

    private Account registerFinancialAccount(Account account) {
        return accountRepository.save(account);
    }
}
