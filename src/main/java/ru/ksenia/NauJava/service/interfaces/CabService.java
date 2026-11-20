package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.dto.CabRequest;
import ru.ksenia.NauJava.dto.CabResponse;
import ru.ksenia.NauJava.entity.Cab;

import java.util.List;

public interface CabService {
    CabResponse addCab(CabRequest request, Long userId);
    CabResponse updateCab(CabRequest newData, Long id, Long userId);
    void deleteCab(Long id, Long userId);
    CabResponse getCabById(Long id, Long userId);
    List<CabResponse> getAllCabsByUser(Long userId);
    List<CabResponse> getAllCabsAdmin();
    void deleteCabAdmin(Long id);
}
