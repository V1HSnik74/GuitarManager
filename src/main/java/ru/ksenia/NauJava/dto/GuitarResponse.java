package ru.ksenia.NauJava.dto;

import ru.ksenia.NauJava.entity.Guitar;

public record GuitarResponse (
    Long id,
    String brand,
    String model,
    int stringsCount,
    String pickupType,
    String guitarType,
    String description,
    String imageUrl
) {
    public static GuitarResponse fromEntity(Guitar guitar) {
        return new GuitarResponse(
                guitar.getId(),
                guitar.getBrand(),
                guitar.getModel(),
                guitar.getStringsCount(),
                guitar.getPickupType(),
                guitar.getGuitarType(),
                guitar.getDescription(),
                guitar.getImageUrl()
        );
    }
}
