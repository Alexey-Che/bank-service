package org.example.bankservice.controller;

import org.example.bankservice.dto.ValidationErrors;
import org.example.bankservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrors handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + " " + errorMessage);
        });
        return new ValidationErrors(errors);
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<?> handleUserRegistrationException(UserRegistrationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrors(e.getErrors()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
    }

    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<?> handleUserEmailNotFoundException(UserEmailNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email не найден");
    }

    @ExceptionHandler(UserPhoneNotFoundException.class)
    public ResponseEntity<?> handleUserPhoneNotFoundException(UserPhoneNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Номер телефона не найден");
    }

    @ExceptionHandler(UserAccountNotFoundException.class)
    public ResponseEntity<?> handleUserAccountNotFoundException(UserAccountNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Банковский аккаунт не найден");
    }

    @ExceptionHandler(TransferMoneyException.class)
    public ResponseEntity<?> handleTransferMoneyException(TransferMoneyException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getError());
    }

    @ExceptionHandler(UserContactAlreadyExistException.class)
    public ResponseEntity<?> handleUserContactAlreadyExistException(UserContactAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UserSearchQueryException.class)
    public ResponseEntity<?> handleUserSearchQueryException(UserSearchQueryException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Некорректный запрос поиска: " + e.getString());
    }
}
