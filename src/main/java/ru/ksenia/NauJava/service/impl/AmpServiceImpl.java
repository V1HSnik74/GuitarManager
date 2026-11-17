package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Amp addAmp(Amp amp, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        amp.setUser(user);
        return ampRepository.save(amp);
    }

    @Override
    @Transactional
    public Amp updateAmp(Amp newData, Long id, Long userId) {
        Amp amp = getAmp(id, userId);
        amp.setAmpType(newData.getAmpType());
        amp.setBrand(newData.getBrand());
        amp.setDescription(newData.getDescription());
        amp.setModel(newData.getModel());
        return ampRepository.save(amp);
    }

    @Override
    @Transactional
    public void deleteAmp(Long id, Long userId) {
        Amp amp = getAmpById(id, userId);
        throwIfExists(id);
        ampRepository.delete(amp);
    }

    @Override
    public Amp getAmpById(Long id, Long userId) {
        return getAmp(id, userId);
    }

    @Override
    public List<Amp> getAmpsByUserId(Long userId) {
        return ampRepository.findAllByUserId(userId);
    }

    @Override
    public List<Amp> getAllAmpsAdmin() {
        return ampRepository.findAll();
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
}
