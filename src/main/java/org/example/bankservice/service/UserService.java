package org.example.bankservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.example.bankservice.domain.User;
import org.example.bankservice.exception.UserNotFoundException;
import org.example.bankservice.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByEmailAndPassword(String email, String password) {
        val userEntity = findByEmail(email);
        if (userEntity.isPresent() && passwordEncoder.matches(password, userEntity.get().getPassword())) {
            return userEntity.get();
        }
        return null;
    }

    public Long getCurrentUserId() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return userRepository.findUserIdByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);
        }

        throw new UserNotFoundException();
    }

    public User getCurrentUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return userRepository.findByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);
        }

        throw new UserNotFoundException();
    }


}
