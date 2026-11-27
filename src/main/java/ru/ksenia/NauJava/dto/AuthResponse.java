package ru.ksenia.NauJava.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}
