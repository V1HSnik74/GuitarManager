package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.dto.SetupRequest;
import ru.ksenia.NauJava.dto.SetupResponse;
import ru.ksenia.NauJava.entity.PedalSlot;
import ru.ksenia.NauJava.entity.Setup;

import java.util.List;

public interface SetupService {
    List<SetupResponse> getAllSetups(Long userId);
    SetupResponse addSetup(SetupRequest setup, Long userId);
    void deleteSetup(Long setupId, Long userId);
    SetupResponse updateSetup(SetupRequest newSetup, Long setupId, Long userId);
    SetupResponse getSetupById(Long setupId, Long userId);
    List<SetupResponse> getAllSetupsAdmin();
    void deleteSetupAdmin(Long setupId);
}
