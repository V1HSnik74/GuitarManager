package ru.ksenia.NauJava.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(int status, String message, String code) {
    public ErrorResponse(int status, String message) {
        this(status, message, null);
    }
}
