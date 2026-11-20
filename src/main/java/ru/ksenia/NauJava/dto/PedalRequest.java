package ru.ksenia.NauJava.dto;

import ru.ksenia.NauJava.enums.PedalTypes;

public record PedalRequest(
        String brand,
        String model,
        PedalTypes pedalType,
        String description
) {
}
