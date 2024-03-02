package org.example.bankservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppStatusController {

    @GetMapping("isAlive")
    public ResponseEntity<?> isAlive() {
        return ResponseEntity.ok().body("OK");
    }
}
