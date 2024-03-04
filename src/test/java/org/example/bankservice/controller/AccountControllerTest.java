package org.example.bankservice.controller;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.val;
import org.example.bankservice.BankServiceApplicationTest;
import org.example.bankservice.PostrgesDatabaseTests;
import org.example.bankservice.dto.TransferMoneyDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends BankServiceApplicationTest implements PostrgesDatabaseTests {

    @Test
    @Transactional
    @DisplayName("Перевод денег с аккаунта на аккаунт")
    @SneakyThrows
    void transferMoney() {
        val request = TransferMoneyDto.builder()
                .amount(10.03)
                .recipientUserId(2)
                .build();

        mockMvc.perform(post("/v1/account/transfer")
                        .header(ACCESS_HEADER, ACCESS_TOKEN_USER1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DisplayName("На аккаунте недостаточно средств для перевода")
    @SneakyThrows
    void transferTooMuchMoney() {
        val request = TransferMoneyDto.builder()
                .amount(1000.03)
                .recipientUserId(2)
                .build();

        mockMvc.perform(post("/v1/account/transfer")
                        .header(ACCESS_HEADER, ACCESS_TOKEN_USER1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("Перевод денег на несуществующий аккаунт")
    @SneakyThrows
    void transferMoneyToNotExistentUser() {
        val request = TransferMoneyDto.builder()
                .amount(10.03)
                .recipientUserId(100)
                .build();

        mockMvc.perform(post("/v1/account/transfer")
                        .header(ACCESS_HEADER, ACCESS_TOKEN_USER1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}