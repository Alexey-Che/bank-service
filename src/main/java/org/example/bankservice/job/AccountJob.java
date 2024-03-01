package org.example.bankservice.job;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.bankservice.service.AccountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountJob {

    AccountService accountService;

    @Scheduled(cron = "${balance-increase-job}")
    public void increaseBalanceForAllAccounts() {
        accountService.increaseBalanceForAllAccounts();
    }
}
