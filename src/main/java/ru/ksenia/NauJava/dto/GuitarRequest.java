package ru.ksenia.NauJava.dto;

public record GuitarRequest(
        String brand,
        String model,
        int stringsCount,
        String pickupType,
        String guitarType,
        String description,
        String imageUrl
) {}
