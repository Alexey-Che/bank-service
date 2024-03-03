package org.example.bankservice.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.example.bankservice.domain.Account;
import org.example.bankservice.exception.TransferMoneyException;
import org.example.bankservice.exception.UserEmailNotFoundException;
import org.example.bankservice.repository.AccountRepository;
import org.example.bankservice.repository.UserRepository;
import org.example.bankservice.util.DoubleUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;
    UserRepository userRepository;
    UserService userService;

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

    @Transactional
    public synchronized void transferMoney(double amount, long recipientUserId) {
        val currentUser = userService.getCurrentUser();

        var account = accountRepository.findByUser(currentUser);

        if (account.getBalance() < amount) {
            throw new TransferMoneyException("Недостаточно средств для перевода");
        }

        var recipientUserAccount = userRepository.findByIdFetchAccount(recipientUserId)
                .orElseThrow(UserEmailNotFoundException::new)
                .getAccount();
        account.setBalance(DoubleUtil.formatDouble(account.getBalance() - amount));
        recipientUserAccount.setBalance(DoubleUtil.formatDouble(Double.sum(recipientUserAccount.getBalance(), amount)));
    }

}
