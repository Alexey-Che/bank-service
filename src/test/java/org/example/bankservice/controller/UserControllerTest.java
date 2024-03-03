package org.example.bankservice.controller;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.example.bankservice.AbstractIntegrationTest;
import org.example.bankservice.PostrgesDatabaseTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest extends AbstractIntegrationTest implements PostrgesDatabaseTests {

    @Transactional
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
    void addContacts() {
    }

    @Test
    void changeContacts() {
    }

    @Test
    void removePhone() {
    }
}