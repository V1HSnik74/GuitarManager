package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ksenia.NauJava.dto.AmpRequest;
import ru.ksenia.NauJava.dto.AmpResponse;
import ru.ksenia.NauJava.entity.Amp;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.repository.AmpRepository;
import ru.ksenia.NauJava.repository.SetupRepository;
import ru.ksenia.NauJava.repository.UserRepository;
import ru.ksenia.NauJava.service.interfaces.AmpService;

import java.util.List;

@Service
public class AmpServiceImpl implements AmpService {
    private final AmpRepository ampRepository;
    private final SetupRepository setupRepository;
    private final UserRepository userRepository;

    @Autowired
    public AmpServiceImpl(AmpRepository ampRepository, SetupRepository setupRepository, UserRepository userRepository) {
        this.ampRepository = ampRepository;
        this.setupRepository = setupRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AmpResponse addAmp(AmpRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        Amp amp = new Amp();
        amp.setUser(user);
        applyFields(amp, request);
        return AmpResponse.fromEntity(ampRepository.save(amp));
    }

    @Override
    @Transactional
    public AmpResponse updateAmp(AmpRequest newData, Long id, Long userId) {
        Amp amp = getAmp(id, userId);
        applyFields(amp, newData);
        return AmpResponse.fromEntity(ampRepository.save(amp));
    }

    @Override
    @Transactional
    public void deleteAmp(Long id, Long userId) {
        Amp amp = getAmp(id, userId);
        throwIfExists(id);
        ampRepository.delete(amp);
    }

    @Override
    public AmpResponse getAmpById(Long id, Long userId) {
        return AmpResponse.fromEntity(getAmp(id, userId));
    }

    @Override
    public List<AmpResponse> getAmpsByUserId(Long userId) {
        return ampRepository.findAllByUserId(userId).stream()
                .map(AmpResponse::fromEntity)
                .toList();
    }

    @Override
    public List<AmpResponse> getAllAmpsAdmin() {
        return ampRepository.findAll().stream()
                .map(AmpResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public void deleteAmpAdmin(Long id) {
        throwIfExists(id);
        ampRepository.deleteById(id);
    }

    private Amp getAmp(Long id, Long userId) {
        return ampRepository.findByIdAndUser(id, userId).orElseThrow(() -> new ResourceNotFoundException("Усилитель не найден"));
    }

    private void throwIfExists(Long id) {
        if (setupRepository.existsByAmpId(id)) {
            throw new IllegalStateException("Нельзя удалить усилитель, так как он используется в пресетах");
        }
    }

    private void applyFields(Amp amp, AmpRequest data) {
        amp.setModel(data.model());
        amp.setBrand(data.brand());
        amp.setAmpType(data.ampType());
        amp.setDescription(data.description());
    }
}
