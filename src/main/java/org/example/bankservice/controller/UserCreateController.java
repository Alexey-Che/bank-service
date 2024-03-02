package org.example.bankservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class UserCreateController {

    @PostMapping("/user")
    public ResponseEntity<?> createUser() {
        return ResponseEntity.ok().build();
    }

}
