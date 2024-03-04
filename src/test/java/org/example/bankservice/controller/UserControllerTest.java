package org.example.bankservice.controller;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.val;
import org.example.bankservice.BankServiceApplicationTest;
import org.example.bankservice.PostrgesDatabaseTests;
import org.example.bankservice.dto.AddUserContactDto;
import org.example.bankservice.dto.ChangeContactDto;
import org.example.bankservice.dto.DeleteUserContactDto;
import org.example.bankservice.repository.EmailRepository;
import org.example.bankservice.repository.PhoneNumberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

class UserControllerTest extends BankServiceApplicationTest implements PostrgesDatabaseTests {

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

    @ParameterizedTest
    @CsvSource(value = {
            "add_email@test.com,",
            ",+79111222333",
            "add_email@test.com,+79111222333"
    })
    @DisplayName("Добавление контактов пользователю")
    @Transactional
    @SneakyThrows
    void addContacts(String email, String phone) {
        val request = AddUserContactDto.builder()
                .email(email)
                .phoneNumber(phone)
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

    @ParameterizedTest
    @CsvSource(value = {
            "test@test.com,,newEmail@test.com,",
            ",+79123456789,,+79000111444",
            "test@test.com,+79123456789,newEmail@test.com,+79000111444"
    })
    @DisplayName("Смена номера телефона и email пользователя")
    @Transactional
    @SneakyThrows
    void changeContacts(String oldEmail, String oldPhone, String newEmail, String newPhone) {
        val request = ChangeContactDto.builder()
                .oldPhoneNumber(oldPhone)
                .newPhoneNumber(newPhone)
                .oldEmail(oldEmail)
                .newEmail(newEmail)
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

        if (newEmail != null) {
            val updatedEmails = emailRepository.findAllByUserId(1L);
            assertEquals(updatedEmails.size(), 1);
            assertEquals(newEmail, updatedEmails.get(0).getEmail());
        }

        if (newPhone != null) {
            val updatedPhones = phoneNumberRepository.findAllByUserId(1L);
            assertEquals(updatedPhones.size(), 1);
            assertEquals(newPhone, updatedPhones.get(0).getPhone());
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "test_email1@test.com,",
            ",+79123456001",
            "test_email1@test.com,+79123456001"
    })
    @DisplayName("Удаление номера телефона и email пользователя")
    @Transactional
    @SneakyThrows
    void removePhone(String email, String phone) {
        val request = DeleteUserContactDto.builder()
                .email(email)
                .phoneNumber(phone)
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

        if (email != null) {
            val updatedEmails = emailRepository.findAllByUserId(2L);
            assertEquals(updatedEmails.size(), 2);
        }

        if (phone != null) {
            val updatedPhones = phoneNumberRepository.findAllByUserId(2L);
            assertEquals(updatedPhones.size(), 2);
        }
    }

}