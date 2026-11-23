package ru.ksenia.NauJava.dto;

import ru.ksenia.NauJava.entity.Amp;
import ru.ksenia.NauJava.enums.AmpTypes;

public record AmpResponse(
        Long id,
        String brand,
        String model,
        AmpTypes ampType,
        String description,
        String imageUrl
) {
    public static AmpResponse fromEntity(Amp amp) {
        return new AmpResponse(
                amp.getId(),
                amp.getBrand(),
                amp.getModel(),
                amp.getAmpType(),
                amp.getDescription(),
                amp.getImageUrl()
        );
    }
}
