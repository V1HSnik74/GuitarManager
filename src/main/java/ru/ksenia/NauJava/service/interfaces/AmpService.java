package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.dto.AmpRequest;
import ru.ksenia.NauJava.dto.AmpResponse;

import java.util.List;

public interface AmpService {
    AmpResponse addAmp(AmpRequest request, Long userId);
    AmpResponse updateAmp(AmpRequest newData, Long id, Long userId);
    void deleteAmp(Long id, Long userId);
    AmpResponse getAmpById(Long id, Long userId);
    List<AmpResponse> getAmpsByUserId(Long userId);
    List<AmpResponse> getAllAmpsAdmin();
    void deleteAmpAdmin(Long id);
}
