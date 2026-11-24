package ru.ksenia.NauJava.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PedalSlotRequest(
        @NotNull(message = "ID педали обязателен")
        @Positive(message = "ID педали должен быть положительным")
        Long pedalId,

        @Size(max = 1000, message = "Настройки педали не должны превышать 1000 символов")
        String config
) {
}
