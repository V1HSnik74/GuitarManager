package ru.ksenia.NauJava.dto;

import ru.ksenia.NauJava.entity.Cab;

public record CabResponse(
        Long id,
        String brand,
        String model,
        String speakers,
        String description,
        String imageUrl
) {
    public static CabResponse fromEntity(Cab cab) {
        return new CabResponse(
                cab.getId(),
                cab.getBrand(),
                cab.getModel(),
                cab.getSpeakers(),
                cab.getDescription(),
                cab.getImageUrl()
        );
    }
}
