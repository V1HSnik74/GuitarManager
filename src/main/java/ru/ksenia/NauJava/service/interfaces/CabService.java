package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.entity.Cab;

import java.util.List;

public interface CabService {
    Cab addCab(Cab cab, Long userId);
    Cab updateCab(Cab newData, Long id, Long userId);
    void deleteCab(Long id, Long userId);
    Cab getCabById(Long id, Long userId);
    List<Cab> getAllCabsByUser(Long userId);
    List<Cab> getAllCabsAdmin();
    void deleteCabAdmin(Long id);
}
