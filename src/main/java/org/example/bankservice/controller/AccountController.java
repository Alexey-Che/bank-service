package org.example.bankservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.bankservice.dto.TransferMoneyDto;
import org.example.bankservice.service.AccountService;
import org.example.bankservice.util.DoubleUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestBody @Valid TransferMoneyDto transferMoneyDto) {
        accountService.transferMoney(DoubleUtil.formatDouble(transferMoneyDto.getAmount()),
                transferMoneyDto.getRecipientUserId());
        return ResponseEntity.ok().build();
    }

}
