package org.example.bankservice.dto;

import java.util.List;

public record ValidationErrors(
        List<String> errors
) {
}
