package org.example.bankservice.controller;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.val;
import org.example.bankservice.AbstractIntegrationTest;
import org.example.bankservice.PostrgesDatabaseTests;
import org.example.bankservice.dto.AddUserContactDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest extends AbstractIntegrationTest implements PostrgesDatabaseTests {

    @DisplayName("Поиск пользователей по номеру телефона, имени, email, дате рождения")
    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"+79123456789", "surname", "test@test.com", "1990-05-15"})
    void searchUser(String query) {
        mockMvc.perform(get("/v1/user/search")
                        .header(ACCESS_HEADER, ACCESS_TOKEN)
                        .param("query", query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    @DisplayName("Добавление контактов пользователю")
    @Transactional
    @SneakyThrows
    void addContacts() {
        val request = AddUserContactDto.builder()
                .email("add_email@test.com")
                .phoneNumber("+79111222333")
                .build();

        mockMvc.perform(post("/v1/user/update-contact/add")
                        .header(ACCESS_HEADER, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Добавление к контактам уже существующего email")
    @Transactional
    @SneakyThrows
    void emailAlreadyExist() {
        val request = AddUserContactDto.builder()
                .email("test@test.com")
                .build();

        mockMvc.perform(post("/v1/user/update-contact/add")
                        .header(ACCESS_HEADER, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Добавление к контактам уже существующего номера телефона")
    @Transactional
    @SneakyThrows
    void phoneAlreadyExist() {
        val request = AddUserContactDto.builder()
                .phoneNumber("+79123456789")
                .build();

        mockMvc.perform(post("/v1/user/update-contact/add")
                        .header(ACCESS_HEADER, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void changeContacts() {
    }

    @Test
    void removePhone() {
    }
}