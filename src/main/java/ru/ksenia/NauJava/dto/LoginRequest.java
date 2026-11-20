package ru.ksenia.NauJava.dto;

public record LoginRequest(
        String login,
        String password
) {
}
