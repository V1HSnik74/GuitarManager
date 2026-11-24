package ru.ksenia.NauJava.dto;

import ru.ksenia.NauJava.entity.Setup;

import java.util.List;

public record SetupResponse(
        Long id,
        String name,
        String description,
        GuitarResponse guitar,
        AmpResponse amp,
        CabResponse cab,
        AmpConfig ampConfig,
        List<PedalSlotResponse> pedalboard,
        String imageUrl
) {
    public static SetupResponse fromEntity(Setup setup) {
        return new SetupResponse(
                setup.getId(),
                setup.getName(),
                setup.getDescription(),
                setup.getGuitar() != null ? GuitarResponse.fromEntity(setup.getGuitar()) : null,
                setup.getAmp() != null ? AmpResponse.fromEntity(setup.getAmp()) : null,
                setup.getCab() != null ? CabResponse.fromEntity(setup.getCab()) : null,
                setup.getAmpConfig(),
                setup.getPedalboard() == null ? List.of() : setup.getPedalboard().stream()
                        .map(PedalSlotResponse::fromEntity)
                        .toList(),
                setup.getImageUrl()
        );
    }
}
