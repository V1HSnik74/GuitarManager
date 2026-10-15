package ru.ksenia.NauJava.service;

import ru.ksenia.NauJava.entity.PedalSlot;

import java.util.List;

public interface SetupPedalboardService {
    List<PedalSlot> updatePedalChain(Long setupId, List<Long> pedalIds);
}
