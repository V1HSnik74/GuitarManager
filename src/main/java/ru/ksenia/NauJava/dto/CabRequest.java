package ru.ksenia.NauJava.dto;

public record CabRequest(
        String brand,
        String model,
        String speakers,
        String description,
        String imageUrl
)
{}
