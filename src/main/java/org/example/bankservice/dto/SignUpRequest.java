package org.example.bankservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    String username;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    String password;

    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    String email;

    @Schema(description = "номер телефона", example = "+79123456789")
    @Pattern(regexp = "\\+\\d{11}", message = "Номер телефона нужно указать в формате +7XXXXXXXXXX")
    @NotBlank(message = "Номер телефона не может быть пустыми")
    String phone;

    @Schema(description = "начальный баланс аккаунта", example = "100.05")
    @NotNull(message = "Нужно указать начальный баланс аккаунта")
    Double balance;

    @Schema(description = "ФИО", example = "Иванов Иван Иванович")
    @Size(min = 5, max = 255, message = "ФИО должен содержать от 5 до 255 символов")
    @NotBlank(message = "ФИО не может быть пустыми")
    String fullName;

    @Schema(description = "Дата рождения", example = "1999-05-15")
    Date birthDate;

}
