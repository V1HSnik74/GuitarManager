package ru.ksenia.NauJava.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Логин не должен быть пустым")
        @Size(min = 3, max = 50, message = "Логин должен содержать от 3 до 50 символов")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Логин может содержать только латинские буквы, цифры, точки, дефисы и подчёркивания")
        String username,

        @NotBlank(message = "Электронная почта обязательна")
        @Email(message = "Некорректный формат электронной почты")
        @Size(max = 100, message = "Электронная почта не должна превышать 100 символов")
        String email,

        @NotBlank(message = "Пароль не должен быть пустым")
        @Size(min = 8, max = 72, message = "Пароль должен содержать от 8 до 72 символов")
        String password
) {
}
