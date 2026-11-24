package ru.ksenia.NauJava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Логин или email обязателен")
        @Size(max = 50, message = "Логин не должен превышать 50 символов")
        String login,

        @NotBlank(message = "Пароль обязателен")
        @Size(max = 72, message = "Пароль не должен превышать 72 символа")
        String password
) {
}
