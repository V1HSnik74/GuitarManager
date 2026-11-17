package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.entity.Amp;

import java.util.List;

public interface AmpService {
    Amp addAmp(Amp amp, Long userId);
    Amp updateAmp(Amp newData, Long id, Long userId);
    void deleteAmp(Long id, Long userId);
    Amp getAmpById(Long id, Long userId);
    List<Amp> getAmpsByUserId(Long userId);
    List<Amp> getAllAmpsAdmin();
    void deleteAmpAdmin(Long id);
}
