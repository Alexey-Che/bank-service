package org.example.bankservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Операции с банковским аккаунтом пользователя")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @Operation(summary = "Перевод денег с одного счета на другой")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Произведен перевод денежных средств"),
            @ApiResponse(responseCode = "400", description = "Не достаточно средств для перевода"),
            @ApiResponse(responseCode = "404", description = "Не найден пользователь и/или аккаунт")
    })
    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestBody @Valid TransferMoneyDto transferMoneyDto) {
        accountService.transferMoney(DoubleUtil.formatDouble(transferMoneyDto.getAmount()),
                transferMoneyDto.getRecipientUserId());
        return ResponseEntity.ok().build();
    }

}
