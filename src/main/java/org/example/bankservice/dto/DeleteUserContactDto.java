package org.example.bankservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteUserContactDto {

    @Schema(description = "номер телефона", example = "+79123456789")
    @Pattern(regexp = "\\+\\d{11}", message = "Номер телефона нужно указать в формате +7XXXXXXXXXX")
    String phoneNumber;

    @Schema(description = "Адрес электронной почты", example = "test@test.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    String email;
}
