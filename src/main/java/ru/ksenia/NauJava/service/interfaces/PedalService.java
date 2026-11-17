package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.entity.Pedal;

import java.util.List;

public interface PedalService {
    Pedal addPedal(Pedal pedal, Long userId);
    Pedal updatePedal(Pedal newData, Long id, Long userId);
    void deletePedal(Long id, Long userId);
    Pedal getPedalById(Long id, Long userId);
    List<Pedal> getPedalsByUserId(Long userId);
    List<Pedal> getAllPedalsAdmin();
    void deletePedalAdmin(Long id);
}
