package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ksenia.NauJava.dto.PedalSlotRequest;
import ru.ksenia.NauJava.dto.SetupRequest;
import ru.ksenia.NauJava.dto.SetupResponse;
import ru.ksenia.NauJava.entity.Pedal;
import ru.ksenia.NauJava.entity.PedalSlot;
import ru.ksenia.NauJava.entity.Setup;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.repository.*;
import ru.ksenia.NauJava.service.interfaces.SetupService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetupServiceImpl implements SetupService {
    private final SetupRepository setupRepository;
    private final UserRepository userRepository;
    private final PedalRepository pedalRepository;
    private final GuitarRepository guitarRepository;
    private final AmpRepository ampRepository;
    private final CabRepository cabRepository;

    @Autowired
    public SetupServiceImpl(SetupRepository setupRepository, UserRepository userRepository, PedalRepository pedalRepository, GuitarRepository guitarRepository, AmpRepository ampRepository, CabRepository cabRepository) {
        this.setupRepository = setupRepository;
        this.userRepository = userRepository;
        this.pedalRepository = pedalRepository;
        this.guitarRepository = guitarRepository;
        this.ampRepository = ampRepository;
        this.cabRepository = cabRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SetupResponse> getAllSetups(Long userId) {
        return setupRepository.findAllByUserId(userId).stream()
                .map(SetupResponse::fromEntity).toList();
    }

    @Override
    @Transactional
    public SetupResponse addSetup(SetupRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        Setup setup = new Setup();
        setup.setUser(user);
        applyFields(setup, request, userId);
        return SetupResponse.fromEntity(setupRepository.save(setup));
    }

    @Override
    @Transactional
    public void deleteSetup(Long setupId, Long userId) {
        Setup setup = getSetup(setupId, userId);
        setupRepository.delete(setup);
    }

    @Override
    @Transactional
    public SetupResponse updateSetup(SetupRequest newSetup, Long setupId, Long userId) {
        Setup setup = getSetup(setupId, userId);
        applyFields(setup, newSetup, userId);
        return SetupResponse.fromEntity(setupRepository.save(setup));
    }

    @Override
    @Transactional(readOnly = true)
    public SetupResponse getSetupById(Long setupId, Long userId) {
        return SetupResponse.fromEntity(getSetup(setupId, userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SetupResponse> getAllSetupsAdmin() {
        return setupRepository.findAll().stream()
                .map(SetupResponse::fromEntity).toList();
    }

    @Override
    @Transactional
    public void deleteSetupAdmin(Long setupId) {
        setupRepository.deleteById(setupId);
    }

    private void applyPedalboard(Setup setup, Long userId, List<PedalSlotRequest> newData) {
        if (setup.getPedalboard() == null) {
            setup.setPedalboard(new ArrayList<>());
        }
        setup.getPedalboard().clear();
        if (newData == null || newData.isEmpty()) {
            return;
        }
        for (int i = 0; i < newData.size(); i++) {
            PedalSlotRequest newSlot = newData.get(i);
            Pedal pedal = pedalRepository.findByIdAndUserId(newSlot.pedalId(), userId).orElseThrow(() -> new ResourceNotFoundException("Педаль не найдена"));
            PedalSlot slot = new PedalSlot(
                    setup, pedal, i, pedal.getPedalType().getChainZone(), newSlot.config()
            );
            setup.getPedalboard().add(slot);
        }
    }

    private Setup getSetup(Long id, Long userId) {
        return setupRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Пресет не найден"));
    }

    private void applyFields(Setup setup, SetupRequest data, Long userId) {
        setup.setName(data.name());
        setup.setDescription(data.description());
        setup.setAmpConfig(data.ampConfig());
        setup.setGuitar(data.guitarId() == null ? null
                : guitarRepository.findByIdAndUserId(data.guitarId(), userId).orElseThrow(
                () -> new ResourceNotFoundException("Гитара не найдена")
        ));
        setup.setAmp(data.ampId() == null ? null
                : ampRepository.findByIdAndUserId(data.ampId(), userId).orElseThrow(
                () -> new ResourceNotFoundException("Усилитель не найден")
        ));
        setup.setCab(data.cabId() == null ? null
                : cabRepository.findByIdAndUserId(data.cabId(), userId).orElseThrow(
                () -> new ResourceNotFoundException("Кабинет не найден")
        ));
        applyPedalboard(setup, userId, data.pedalboard());
        setup.setImageUrl(data.imageUrl());
    }

}
