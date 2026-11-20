package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.dto.PedalRequest;
import ru.ksenia.NauJava.dto.PedalResponse;

import java.util.List;

public interface PedalService {
    PedalResponse addPedal(PedalRequest request, Long userId);
    PedalResponse updatePedal(PedalRequest newData, Long id, Long userId);
    void deletePedal(Long id, Long userId);
    PedalResponse getPedalById(Long id, Long userId);
    List<PedalResponse> getPedalsByUserId(Long userId);
    List<PedalResponse> getAllPedalsAdmin();
    void deletePedalAdmin(Long id);
}
