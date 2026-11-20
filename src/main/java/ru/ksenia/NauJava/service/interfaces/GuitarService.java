package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.dto.GuitarRequest;
import ru.ksenia.NauJava.dto.GuitarResponse;

import java.util.List;

public interface GuitarService {
    GuitarResponse addGuitar(GuitarRequest request, Long userId);
    GuitarResponse updateGuitar(GuitarRequest newData, Long guitarId, Long userId);
    void deleteGuitar(Long id, Long userId);
    GuitarResponse getGuitarById(Long id, Long userId);
    List<GuitarResponse> getAllGuitarsByUserId(Long userId);
    List<GuitarResponse> getAllGuitarsAdmin();
    void deleteGuitarAdmin(Long guitarId);
}
