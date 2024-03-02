package org.example.bankservice.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.example.bankservice.domain.Email;
import org.example.bankservice.domain.PhoneNumber;
import org.example.bankservice.domain.User;
import org.example.bankservice.dto.AddUserContactDto;
import org.example.bankservice.exception.UserEmailNotFoundException;
import org.example.bankservice.exception.UserPhoneNotFoundException;
import org.example.bankservice.exception.UserRegistrationException;
import org.example.bankservice.repository.EmailRepository;
import org.example.bankservice.repository.PhoneNumberRepository;
import org.example.bankservice.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository repository;
    EmailRepository emailRepository;
    PhoneNumberRepository phoneNumberRepository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        List<String> errors = new ArrayList<>();

        if (repository.existsByUsername(user.getUsername())) {
            errors.add("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(getEmails(user))) {
            errors.add("Пользователь с таким email уже существует");
        }

        if (repository.existsByPhoneNumbers(getPhoneNumbers(user))) {
            errors.add("Пользователь с таким номером телефона уже существует");
        }

        if (!errors.isEmpty()) {
            throw new UserRegistrationException(errors);
        }

        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Transactional
    public void addContacts(String email, String phone) {
        var currentUser = getCurrentUser();

        if (email != null && !emailRepository.findAllByUserId(currentUser.getId()).contains(email)) {
            currentUser.getEmails().add(new Email(email));
        }

        if (phone != null && !phoneNumberRepository.findAllByUserId(currentUser.getId()).contains(phone)) {
            currentUser.getPhoneNumbers().add(new PhoneNumber(phone));
        }
    }

    @Transactional
    public void deleteContacts(String email, String phone) {
        var currentUser = getCurrentUser();

        if (email != null && emailRepository.findAllByUserId(currentUser.getId()).size() < 2) {
            var userEmail = emailRepository.findByEmailAndUserId(email, currentUser.getId())
                    .orElseThrow(UserEmailNotFoundException::new);
            emailRepository.delete(userEmail);
        }

        if (phone != null && phoneNumberRepository.findAllByUserId(currentUser.getId()).size() < 2) {
            var phoneNumber = phoneNumberRepository.findByPhoneAndUserId(phone, currentUser.getId())
                    .orElseThrow(UserPhoneNotFoundException::new);
            phoneNumberRepository.delete(phoneNumber);
        }
    }

    private List<String> getEmails(User user) {
        return user.getEmails().stream()
                .map(Email::getEmail)
                .toList();
    }

    private List<String> getPhoneNumbers(User user) {
        return user.getPhoneNumbers().stream()
                .map(PhoneNumber::getPhone)
                .toList();
    }
}