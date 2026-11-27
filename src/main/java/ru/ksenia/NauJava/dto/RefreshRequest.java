package ru.ksenia.NauJava.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank(message = "Refresh-токен не должен быть пустым")
        String refreshToken
) {
}
