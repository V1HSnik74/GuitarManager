package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ksenia.NauJava.dto.PedalRequest;
import ru.ksenia.NauJava.dto.PedalResponse;
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
    public PedalResponse addPedal(PedalRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        Pedal pedal = new Pedal();
        pedal.setUser(user);
        applyFields(pedal, request);
        return PedalResponse.fromEntity(pedalRepository.save(pedal));
    }

    @Override
    @Transactional
    public PedalResponse updatePedal(PedalRequest newData, Long id, Long userId) {
        Pedal pedal = getPedal(id, userId);
        applyFields(pedal, newData);
        return PedalResponse.fromEntity(pedalRepository.save(pedal));
    }

    @Override
    @Transactional
    public void deletePedal(Long id, Long userId) {
        Pedal pedal = getPedal(id, userId);
        throwIfUsed(id);
        pedalRepository.delete(pedal);
    }

    @Override
    public PedalResponse getPedalById(Long id, Long userId) {
        return PedalResponse.fromEntity(getPedal(id, userId));
    }

    @Override
    public List<PedalResponse> getPedalsByUserId(Long userId) {
        return pedalRepository.findAllByUserId(userId).stream()
                .map(PedalResponse::fromEntity)
                .toList();
    }

    @Override
    public List<PedalResponse> getAllPedalsAdmin() {
        return pedalRepository.findAll().stream()
                .map(PedalResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public void deletePedalAdmin(Long id) {
        throwIfUsed(id);
        pedalRepository.deleteById(id);
    }

    private Pedal getPedal(Long id, Long userId) {
        return pedalRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Педаль не найдена"));
    }

    private void throwIfUsed(Long id) {
        if (pedalSlotRepository.existsByPedalId(id)) {
            throw new IllegalStateException("Нельзя удалить педаль, так как она используется в пресетах");
        }
    }

    private void applyFields(Pedal pedal, PedalRequest data) {
        pedal.setBrand(data.brand());
        pedal.setModel(data.model());
        pedal.setPedalType(data.pedalType());
        pedal.setDescription(data.description());
        pedal.setImageUrl(data.imageUrl());
    }
}
