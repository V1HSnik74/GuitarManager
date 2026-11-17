package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.entity.Guitar;

import java.util.List;

public interface GuitarService {
    Guitar addGuitar(Guitar guitar, Long userId);
    Guitar updateGuitar(Guitar newData, Long guitarId, Long userId);
    void deleteGuitar(Long id, Long userId);
    Guitar getGuitarById(Long id, Long userId);
    List<Guitar> getAllGuitarsByUserId(Long userId);
    List<Guitar> getAllGuitarsAdmin();
    void deleteGuitarAdmin(Long guitarId);
}
