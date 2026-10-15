package ru.ksenia.NauJava.dao;

import ru.ksenia.NauJava.entity.Setup;
import ru.ksenia.NauJava.enums.PedalTypes;

import java.util.List;

public interface SetupRepositoryCustom {
    List<Setup> findAllContainingPedalType(PedalTypes pedalType);
    boolean existsByUserIdAndName(Long userId, String name);
}
