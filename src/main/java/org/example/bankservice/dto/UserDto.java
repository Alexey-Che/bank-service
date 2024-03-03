package org.example.bankservice.dto;

import java.util.Date;
import java.util.List;

public record UserDto(
        String fullName,
        Date birthDate,
        Double balance,
        List<String> emails,
        List<String> phones
) {
}
