package org.example.bankservice.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.bankservice.domain.Account;
import org.example.bankservice.exception.TransferMoneyException;
import org.example.bankservice.exception.UserAccountNotFoundException;
import org.example.bankservice.exception.UserEmailNotFoundException;
import org.example.bankservice.repository.AccountRepository;
import org.example.bankservice.repository.UserRepository;
import org.example.bankservice.util.DoubleUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;
    UserRepository userRepository;
    UserService userService;

    ReentrantLock lock = new ReentrantLock();

    /**
     * Увеличение баланса на всех аккаунтов на 5%, но не более чем на 207% от начальной величины
     */
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

    /**
     * Перевод денег между аккаунтом авторизованного пользователя другому
     * @param amount количество переводимых денег
     * @param recipientUserId id пользователя кому переводятся деньги
     */
    @Transactional
    public void transferMoney(double amount, long recipientUserId) {
        lock.lock();

        try {
            val currentUser = userService.getCurrentUser();
            log.info("перевод денежных средств пользователя - {}, пользователю - {}, сумма - {}",
                    currentUser.getId(),
                    recipientUserId,
                    amount);

            var account = accountRepository.findByUser(currentUser).orElseThrow(UserAccountNotFoundException::new);
            log.info("баланс отправителя до перевода - {}", account.getBalance());

            if (account.getBalance() < amount) {
                throw new TransferMoneyException("Недостаточно средств для перевода");
            }

            var recipientUserAccount = userRepository.findByIdFetchAccount(recipientUserId)
                    .orElseThrow(UserEmailNotFoundException::new)
                    .getAccount();
            log.info("баланс получателя до перевода - {}", recipientUserAccount.getBalance());

            account.setBalance(DoubleUtil.formatDouble(account.getBalance() - amount));
            log.info("баланс отправителя после перевода - {}", account.getBalance());
            recipientUserAccount.setBalance(DoubleUtil.formatDouble(Double.sum(recipientUserAccount.getBalance(), amount)));
            log.info("баланс получателя после перевода - {}", recipientUserAccount.getBalance());
        } finally {
            lock.unlock();
        }
    }

}
