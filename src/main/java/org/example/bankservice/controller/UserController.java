package org.example.bankservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.example.bankservice.domain.Email;
import org.example.bankservice.domain.PhoneNumber;
import org.example.bankservice.dto.AddUserContactDto;
import org.example.bankservice.dto.ChangeContactDto;
import org.example.bankservice.dto.DeleteUserContactDto;
import org.example.bankservice.dto.UserDto;
import org.example.bankservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "Операции с аккаунтом пользователя")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "3") Integer limit
    ) {
        val foundUsers = userService.searchUser(query, page, limit).stream()
                .map(user -> new UserDto(user.getFullName(),
                        user.getBirthDate(),
                        user.getAccount().getBalance(),
                        user.getEmails().stream().map(Email::getEmail).toList(),
                        user.getPhoneNumbers().stream().map(PhoneNumber::getPhone).toList()))
                .toList();
        return ResponseEntity.ok().body(foundUsers);
    }

    @PostMapping("/update-contact/add")
    public ResponseEntity<?> addContacts(@RequestBody @Valid AddUserContactDto addUserContactDto) {
        userService.addContacts(addUserContactDto.getEmail(), addUserContactDto.getPhoneNumber());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-contact/change")
    public ResponseEntity<?> changeContacts(@RequestBody @Valid ChangeContactDto dto) {
        userService.changeContacts(dto.getOldEmail(),
                dto.getOldPhoneNumber(),
                dto.getNewEmail(),
                dto.getNewPhoneNumber());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-contact")
    public ResponseEntity<?> removePhone(@RequestBody @Valid DeleteUserContactDto deleteUserContactDto) {
        userService.deleteContacts(deleteUserContactDto.getEmail(), deleteUserContactDto.getPhoneNumber());

        return ResponseEntity.ok().build();
    }

}
