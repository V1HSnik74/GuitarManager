package ru.ksenia.NauJava.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AmpConfig(
        Double gain,
        Double bass,
        Double mid,
        Double high,
        Double volume,

        @Size(max = 1000, message = "Дополнительная информация не должна превышать 1000 символов")
        String description
) {
}
