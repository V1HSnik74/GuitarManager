package ru.ksenia.NauJava.dto;

import ru.ksenia.NauJava.entity.Pedal;
import ru.ksenia.NauJava.enums.PedalTypes;

public record PedalResponse(
        Long id,
        String brand,
        String model,
        PedalTypes pedalType,
        String description,
        String imageUrl
) {
    public static PedalResponse fromEntity(Pedal pedal) {
        return new PedalResponse(
                pedal.getId(),
                pedal.getBrand(),
                pedal.getModel(),
                pedal.getPedalType(),
                pedal.getDescription(),
                pedal.getImageUrl()
        );
    }
}
