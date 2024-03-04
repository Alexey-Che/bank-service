package org.example.bankservice.service;

import jakarta.transaction.Transactional;
import org.example.bankservice.BankServiceApplicationTest;
import org.example.bankservice.PostrgesDatabaseTests;
import org.example.bankservice.domain.Account;
import org.example.bankservice.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AccountServiceTest extends BankServiceApplicationTest implements PostrgesDatabaseTests {

    @SpyBean
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    @Transactional
    @DisplayName("Увеличение баланса на всех аккаунтах")
    void increaseBalanceForAllAccounts() {

        List<Double> accounts = accountRepository.findAll().stream().map(Account::getBalance).toList();
        assertEquals(100, accounts.get(0));
        assertEquals(105, accounts.get(1));

        accountService.increaseBalanceForAllAccounts();
        verify(accountService, times(1)).increaseBalanceForAllAccounts();

        List<Double> increased = accountRepository.findAll().stream().map(Account::getBalance).toList();
        assertEquals(105, increased.get(0));
        assertEquals( 110.25, increased.get(1));

    }
}