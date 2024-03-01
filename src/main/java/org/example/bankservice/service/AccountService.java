package org.example.bankservice.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.bankservice.domain.Account;
import org.example.bankservice.repository.AccountRepository;
import org.example.bankservice.util.DoubleUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;

    @Transactional
    public void increaseBalanceForAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        for (var account : accounts) {
            double newBalance = account.getBalance() * 1.05;
            if (newBalance <= account.getInitialBalance() * 2.07) {
                account.setBalance(DoubleUtil.formatDouble(newBalance));
            }
        }
        accountRepository.saveAll(accounts);
    }
}
