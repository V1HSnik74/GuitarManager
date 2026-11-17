package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ksenia.NauJava.entity.Pedal;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.repository.PedalRepository;
import ru.ksenia.NauJava.repository.PedalSlotRepository;
import ru.ksenia.NauJava.repository.UserRepository;
import ru.ksenia.NauJava.service.interfaces.PedalService;

import java.util.List;

@Service
public class PedalServiceImpl implements PedalService {
    private final PedalRepository pedalRepository;
    private final UserRepository userRepository;
    private final PedalSlotRepository pedalSlotRepository;

    @Autowired
    public PedalServiceImpl(PedalRepository pedalRepository, UserRepository userRepository, PedalSlotRepository pedalSlotRepository) {
        this.pedalRepository = pedalRepository;
        this.userRepository = userRepository;
        this.pedalSlotRepository = pedalSlotRepository;
    }

    @Override
    @Transactional
    public Pedal addPedal(Pedal pedal, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        pedal.setUser(user);
        return pedalRepository.save(pedal);
    }

    @Override
    @Transactional
    public Pedal updatePedal(Pedal newData, Long id, Long userId) {
        Pedal pedal = getPedal(id, userId);
        pedal.setPedalType(newData.getPedalType());
        pedal.setModel(newData.getModel());
        pedal.setBrand(newData.getBrand());
        pedal.setDescription(newData.getDescription());
        return pedalRepository.save(pedal);
    }

    @Override
    @Transactional
    public void deletePedal(Long id, Long userId) {
        Pedal pedal = getPedal(id, userId);
        throwIfUsed(id);
        pedalRepository.delete(pedal);
    }

    @Override
    public Pedal getPedalById(Long id, Long userId) {
        return getPedal(id, userId);
    }

    @Override
    public List<Pedal> getPedalsByUserId(Long userId) {
        return pedalRepository.findAllByUserId(userId);
    }

    @Override
    public List<Pedal> getAllPedalsAdmin() {
        return pedalRepository.findAll();
    }

    @Override
    @Transactional
    public void deletePedalAdmin(Long id) {
        throwIfUsed(id);
        pedalRepository.deleteById(id);
    }

    private Pedal getPedal(Long id, Long userId) {
        return pedalRepository.findByIdAndUser(id, userId).orElseThrow(() -> new ResourceNotFoundException("Педаль не найдена"));
    }

    private void throwIfUsed(Long id) {
        if (pedalSlotRepository.existsByPedalId(id)) {
            throw new IllegalStateException("Нельзя удалить педаль, так как она используется в пресетах");
        }
    }
}
