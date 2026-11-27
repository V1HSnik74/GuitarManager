package ru.ksenia.NauJava.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class RefreshTokenException extends AuthenticationException {
    private final String code;
    public RefreshTokenException(String code, String message) {
        super(message);
        this.code = code;
    }
}
