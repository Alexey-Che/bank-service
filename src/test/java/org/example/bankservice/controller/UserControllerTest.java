package org.example.bankservice.controller;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.val;
import org.example.bankservice.AbstractIntegrationTest;
import org.example.bankservice.PostrgesDatabaseTests;
import org.example.bankservice.dto.AddUserContactDto;
import org.example.bankservice.dto.ChangeContactDto;
import org.example.bankservice.dto.DeleteUserContactDto;
import org.example.bankservice.repository.EmailRepository;
import org.example.bankservice.repository.PhoneNumberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractIntegrationTest implements PostrgesDatabaseTests {

    @Autowired
    EmailRepository emailRepository;
    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @DisplayName("Поиск пользователей по номеру телефона, имени, email, дате рождения")
    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"+79123456789", "surname", "test@test.com", "1990-05-15"})
    void searchUser(String query) {
        mockMvc.perform(get("/v1/user/search")
                        .header(ACCESS_HEADER, ACCESS_TOKEN_USER1)
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
                        .header(ACCESS_HEADER, ACCESS_TOKEN_USER1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
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
                        .header(ACCESS_HEADER, ACCESS_TOKEN_USER1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
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
                        .header(ACCESS_HEADER, ACCESS_TOKEN_USER1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Смена номера телефона и email пользователя")
    @Transactional
    @SneakyThrows
    void changeContacts() {
        val request = ChangeContactDto.builder()
                .oldPhoneNumber("+79123456789")
                .newPhoneNumber("+79000111444")
                .oldEmail("test@test.com")
                .newEmail("newEmail@test.com")
                .build();

        val oldEmails = emailRepository.findAllByUserId(1L);
        assertEquals(oldEmails.size(), 1);
        assertEquals("test@test.com", oldEmails.get(0).getEmail());

        val oldPhones = phoneNumberRepository.findAllByUserId(1L);
        assertEquals(oldPhones.size(), 1);
        assertEquals("+79123456789", oldPhones.get(0).getPhone());


        mockMvc.perform(post("/v1/user/update-contact/change")
                        .header(ACCESS_HEADER, ACCESS_TOKEN_USER1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        val updatedEmails = emailRepository.findAllByUserId(1L);
        assertEquals(updatedEmails.size(), 1);
        assertEquals("newEmail@test.com", updatedEmails.get(0).getEmail());

        val updatedPhones = phoneNumberRepository.findAllByUserId(1L);
        assertEquals(updatedPhones.size(), 1);
        assertEquals("+79000111444", updatedPhones.get(0).getPhone());
    }

    @Test
    @DisplayName("Удаление номера телефона и email пользователя")
    @Transactional
    @SneakyThrows
    void removePhone() {
        val request = DeleteUserContactDto.builder()
                .email("test_email1@test.com")
                .phoneNumber("+79123456001")
                .build();

        val oldEmails = emailRepository.findAllByUserId(2L);
        assertEquals(oldEmails.size(), 3);

        val oldPhones = phoneNumberRepository.findAllByUserId(2L);
        assertEquals(oldPhones.size(), 3);

        mockMvc.perform(delete("/v1/user/delete-contact")
                        .header(ACCESS_HEADER, ACCESS_TOKEN_USER2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        val updatedEmails = emailRepository.findAllByUserId(2L);
        assertEquals(updatedEmails.size(), 2);

        val updatedPhones = phoneNumberRepository.findAllByUserId(2L);
        assertEquals(updatedPhones.size(), 2);
    }
}