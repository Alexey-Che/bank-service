package org.example.bankservice.controller;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.example.bankservice.AbstractIntegrationTest;
import org.example.bankservice.PostrgesDatabaseTests;
import org.example.bankservice.dto.SignInRequest;
import org.example.bankservice.dto.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

class AuthControllerTest extends AbstractIntegrationTest implements PostrgesDatabaseTests {

    @Test
    @Transactional
    @DisplayName("Регистрация нового пользователя")
    @SneakyThrows
    void signUp() {
        SignUpRequest request = SignUpRequest.builder()
                .username("test_username")
                .email("test@test.ru")
                .phone("+79123450000")
                .password("test_password")
                .balance(100.05)
                .fullName("Иванов Иван Иванович")
                .birthDate("1999-05-15")
                .build();

        mockMvc.perform(post("/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(not(emptyString())));
    }

    @Test
    @Transactional
    @DisplayName("Авторизация пользователя")
    @SneakyThrows
    void signIn() {
        SignInRequest request = SignInRequest.builder()
                .username("test_user1")
                .password("password")
                .build();

        mockMvc.perform(post("/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(not(emptyString())));
    }

    @Test
    @Transactional
    @DisplayName("Регистрация нового пользователя с невалидными данными")
    @SneakyThrows
    void signUpWithInvalidEmail() {
        SignUpRequest request = SignUpRequest.builder()
                .username("te")
                .email("@test.ru")
                .phone("+791234567891")
                .password("test_password")
                .balance(100.05)
                .fullName("Иванов Иван Иванович")
                .birthDate("1999-05-15111")
                .build();

        mockMvc.perform(post("/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("Авторизация несуществующего пользователя")
    @SneakyThrows
    void signInNotExistentUser() {
        SignInRequest request = SignInRequest.builder()
                .username("user_who_not_exits")
                .password("password")
                .build();

        mockMvc.perform(post("/v1/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
}