package org.example.bankservice.service;

import jakarta.transaction.Transactional;
import org.example.bankservice.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void create() {

        var user = User.builder()
                .username("test_name")
                .password(passwordEncoder.encode("password"))
                .emails(List.of(new Email("test@test.com")))
                .phoneNumbers(List.of(new PhoneNumber("+79000321345")))
                .birthDate(new Date())
                .fullName("Иванов Иван Иванович")
                .account(new Account(100.05))
                .role(UserRole.ROLE_USER)
                .build();

        userService.create(user);

    }
}