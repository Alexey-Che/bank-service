package org.example.bankservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.bankservice.dto.AddUserContactDto;
import org.example.bankservice.dto.ChangeContactDto;
import org.example.bankservice.dto.DeleteUserContactDto;
import org.example.bankservice.mapper.UserMapper;
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


    UserMapper userMapper;

    @Operation(summary = "Поиск пользователей",description = """
            Поиск пользователей по:
            - номеру телефона
            - дате рождения
            - email
            - ФИО
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список найденных пользователей"),
            @ApiResponse(responseCode = "400", description = "Невалидный поисковый запрос")
    })
    @GetMapping("/search")
    public ResponseEntity<?> searchUser(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "3") Integer limit
    ) {

        return ResponseEntity.ok().body(userMapper.doMap(userService.searchUser(query, page, limit)));
    }

    @Operation(summary = "Добавление контактов(номера телефона и/или email)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новые контакты добавлены"),
            @ApiResponse(responseCode = "400", description = "Контакты уже существуют")
    })
    @PostMapping("/update-contact/add")
    public ResponseEntity<?> addContacts(@RequestBody @Valid AddUserContactDto addUserContactDto) {
        userService.addContacts(addUserContactDto.getEmail(), addUserContactDto.getPhoneNumber());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Смена контактов(номера телефона и/или email) пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новые контакты добавлены"),
            @ApiResponse(responseCode = "404", description = "Контакты которые нужно заменить не были найдены")
    })
    @PostMapping("/update-contact/change")
    public ResponseEntity<?> changeContacts(@RequestBody @Valid ChangeContactDto dto) {
        userService.changeContacts(dto.getOldEmail(),
                dto.getOldPhoneNumber(),
                dto.getNewEmail(),
                dto.getNewPhoneNumber());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удаление контактов(номера телефона и/или email)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Контакты успешно удалены"),
            @ApiResponse(responseCode = "404", description = "Контакты не были найдены")
    })
    @DeleteMapping("/delete-contact")
    public ResponseEntity<?> removePhone(@RequestBody @Valid DeleteUserContactDto deleteUserContactDto) {
        userService.deleteContacts(deleteUserContactDto.getEmail(), deleteUserContactDto.getPhoneNumber());

        return ResponseEntity.ok().build();
    }

}
