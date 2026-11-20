package ru.ksenia.NauJava.dto;

import ru.ksenia.NauJava.entity.PedalSlot;
import ru.ksenia.NauJava.enums.ChainZone;

public record PedalSlotResponse(
        Long id,
        PedalResponse pedal,
        int orderIndex,
        ChainZone chainZone,
        String config
) {
    public static PedalSlotResponse fromEntity(PedalSlot slot) {
        return new PedalSlotResponse(
                slot.getId(),
                PedalResponse.fromEntity(slot.getPedal()),
                slot.getOrderIndex(),
                slot.getChainZone(),
                slot.getConfig()
        );
    }
}
