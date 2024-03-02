package org.example.bankservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.example.bankservice.domain.*;
import org.example.bankservice.dto.JwtAuthenticationResponse;
import org.example.bankservice.dto.SignInRequest;
import org.example.bankservice.dto.SignUpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    UserService userService;
    JwtService jwtService;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    @SneakyThrows
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        val birthDate = formatter.parse(request.getBirthDate());

        val user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .emails(List.of(new Email(request.getEmail())))
                .phoneNumbers(List.of(new PhoneNumber(request.getPhone())))
                .birthDate(birthDate)
                .fullName(request.getFullName())
                .account(new Account(request.getBalance()))
                .role(UserRole.ROLE_USER)
                .build();

        userService.create(user);

        val jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        val user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        val jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
