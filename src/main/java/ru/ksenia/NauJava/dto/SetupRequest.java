package ru.ksenia.NauJava.dto;

import java.util.List;

public record SetupRequest(
        String name,
        String description,
        Long guitarId,
        Long ampId,
        Long cabId,
        String ampConfig,
        List<PedalSlotRequest> pedalboard
) {
}
