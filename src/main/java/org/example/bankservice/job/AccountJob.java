package org.example.bankservice.job;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.bankservice.service.AccountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountJob {

    AccountService accountService;

    @Scheduled(cron = "${balance-increase-job}")
    public void increaseBalanceForAllAccounts() {
        log.info("Запуск job увеличения баланса");
        accountService.increaseBalanceForAllAccounts();
        log.info("Баланс на всех аккаунтах увеличен");
    }
}
