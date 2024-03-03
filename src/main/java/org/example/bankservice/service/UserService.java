package org.example.bankservice.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.example.bankservice.domain.Email;
import org.example.bankservice.domain.PhoneNumber;
import org.example.bankservice.domain.User;
import org.example.bankservice.exception.*;
import org.example.bankservice.repository.EmailRepository;
import org.example.bankservice.repository.PhoneNumberRepository;
import org.example.bankservice.repository.UserRepository;
import org.example.bankservice.util.DateUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    EmailRepository emailRepository;
    PhoneNumberRepository phoneNumberRepository;
    QueryService queryService;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return userRepository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        List<String> errors = new ArrayList<>();

        if (userRepository.existsByUsername(user.getUsername())) {
            errors.add("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(getEmails(user))) {
            errors.add("Пользователь с таким email уже существует");
        }

        if (userRepository.existsByPhoneNumbers(getPhoneNumbers(user))) {
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
        return userRepository.findByUsername(username)
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

        val emails = emailRepository.findAll().stream()
                .map(Email::getEmail)
                .toList();

        if (email != null) {
            if (emails.contains(email)) {
                throw new UserContactAlreadyExistException("email уже существует");
            }

            currentUser.getEmails().add(new Email(email));
        }

        val phones = phoneNumberRepository.findAll().stream()
                .map(PhoneNumber::getPhone)
                .toList();

        if (phone != null) {
            if (phones.contains(phone)) {
                throw new UserContactAlreadyExistException("номер телефона уже существует");
            }
            currentUser.getPhoneNumbers().add(new PhoneNumber(phone));
        }
    }

    @Transactional
    public void changeContacts(String oldEmail, String oldPhone, String newEmail, String newPhone) {
        var currentUser = getCurrentUser();

        if (oldEmail != null && newEmail != null) {
            var email = emailRepository.findByEmailAndUserId(oldEmail, currentUser.getId())
                    .orElseThrow(UserEmailNotFoundException::new);
            email.setEmail(newEmail);
            emailRepository.save(email);
        }

        if (oldPhone != null && newPhone != null) {
            var phone = phoneNumberRepository.findByPhoneAndUserId(oldPhone, currentUser.getId())
                    .orElseThrow(UserPhoneNotFoundException::new);
            phone.setPhone(newPhone);
            phoneNumberRepository.save(phone);
        }
    }

    @Transactional
    public void deleteContacts(String email, String phone) {
        var currentUser = getCurrentUser();

        val emails = emailRepository.findAllByUserId(currentUser.getId());
        if (email != null && emails.size() > 1) {
            val userEmail = emails.stream()
                    .filter(e -> e.getEmail().equals(email))
                    .findFirst()
                    .orElseThrow(UserEmailNotFoundException::new);
            emailRepository.delete(userEmail);
        }

        val phoneNumbers = phoneNumberRepository.findAllByUserId(currentUser.getId());
        if (phone != null && phoneNumbers.size() > 1) {
            val phoneNumber = phoneNumbers.stream()
                    .filter(p -> p.getPhone().equals(phone))
                    .findFirst()
                    .orElseThrow(UserPhoneNotFoundException::new);
            phoneNumberRepository.delete(phoneNumber);
        }
    }

    public List<User> searchUser(String query, int page, int limit) {
        val pageRequest = PageRequest.of(page - 1, limit);
        return switch (queryService.detectQueryType(query)) {
            case DATE -> searchUserByDateOfBirth(query, pageRequest);
            case PHONE_NUMBER -> searchUserPhoneNumber(query, pageRequest);
            case EMAIL -> searchUserByEmail(query, pageRequest);
            case FULL_NAME -> searchUserByFullName(query, pageRequest);
            case UNKNOWN -> throw new UserSearchQueryException(query);
        };
    }

    public List<User> searchUserByDateOfBirth(String query, Pageable page) {
        return userRepository.findByBirthDate(DateUtil.stringToDate(query), page);
    }

    public List<User> searchUserByEmail(String query, Pageable page) {
        return userRepository.findByEmail(query, page);
    }

    public List<User> searchUserPhoneNumber(String query, Pageable page) {
        return userRepository.findByPhoneNumber(query, page);
    }

    public List<User> searchUserByFullName(String query, Pageable page) {
        return userRepository.findByFullNameStartsWith(query, page);
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