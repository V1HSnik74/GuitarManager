package ru.ksenia.NauJava.dto;

import ru.ksenia.NauJava.enums.AmpTypes;

public record AmpRequest(
        String brand,
        String model,
        AmpTypes ampType,
        String description,
        String imageUrl
) {
}
