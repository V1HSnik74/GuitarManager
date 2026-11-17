package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.entity.PedalSlot;
import ru.ksenia.NauJava.entity.Setup;

import java.util.List;

public interface SetupService {
    List<Setup> getAllSetups(Long userId);
    Setup addSetup(Setup setup, Long userId);
    void deleteSetup(Long setupId, Long userId);
    Setup updateSetup(Setup newSetup, Long setupId, Long userId);
    Setup getSetupById(Long setupId, Long userId);
    List<Setup> getAllSetupsAdmin();
    void deleteSetupAdmin(Long setupId);
}
