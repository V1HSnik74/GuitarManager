package ru.ksenia.NauJava.dto;

public record RegisterRequest(
        String username,
        String email,
        String password
) {
}
