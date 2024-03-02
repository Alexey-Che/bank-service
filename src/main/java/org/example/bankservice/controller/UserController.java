package org.example.bankservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    @PostMapping("/phone/add")
    public ResponseEntity<?> addPhone() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email/add")
    public ResponseEntity<?> addEmail() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/phone/change")
    public ResponseEntity<?> changePhone() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email/change")
    public ResponseEntity<?> changeEmail() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/phone")
    public ResponseEntity<?> removePhone() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/email")
    public ResponseEntity<?> removeEmail() {
        return ResponseEntity.ok().build();
    }
}
