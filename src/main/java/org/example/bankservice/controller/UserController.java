package org.example.bankservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.bankservice.dto.AddUserContactDto;
import org.example.bankservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/update-contact/add")
    public ResponseEntity<?> addContacts(@RequestBody @Valid AddUserContactDto addUserContactDto) {
        userService.addContacts(addUserContactDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-contact/change")
    public ResponseEntity<?> changeContacts() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-contact")
    public ResponseEntity<?> removePhone() {
        return ResponseEntity.ok().build();
    }

}
