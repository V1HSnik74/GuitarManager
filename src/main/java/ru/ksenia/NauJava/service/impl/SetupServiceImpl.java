package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ksenia.NauJava.entity.Pedal;
import ru.ksenia.NauJava.entity.PedalSlot;
import ru.ksenia.NauJava.entity.Setup;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.repository.PedalRepository;
import ru.ksenia.NauJava.repository.SetupRepository;
import ru.ksenia.NauJava.repository.UserRepository;
import ru.ksenia.NauJava.service.interfaces.PedalService;
import ru.ksenia.NauJava.service.interfaces.SetupService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetupServiceImpl implements SetupService {
    private final SetupRepository setupRepository;
    private final UserRepository userRepository;
    private final PedalRepository pedalRepository;

    @Autowired
    public SetupServiceImpl(SetupRepository setupRepository, UserRepository userRepository, PedalRepository pedalRepository) {
        this.setupRepository = setupRepository;
        this.userRepository = userRepository;
        this.pedalRepository = pedalRepository;
    }

    @Override
    public List<Setup> getAllSetups(Long userId) {
        return setupRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Setup addSetup(Setup setup, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        setup.setUser(user);
        List<PedalSlot> newData = setup.getPedalboard() == null ? List.of() : new ArrayList<>(setup.getPedalboard());
        applyPedalboard(setup, userId, newData);
        return setupRepository.save(setup);
    }

    @Override
    @Transactional
    public void deleteSetup(Long setupId, Long userId) {
        Setup setup = getSetup(setupId, userId);
        setupRepository.delete(setup);
    }

    @Override
    @Transactional
    public Setup updateSetup(Setup newSetup, Long setupId, Long userId) {
        Setup setup = getSetup(setupId, userId);
        setup.setName(newSetup.getName());
        setup.setAmp(newSetup.getAmp());
        setup.setAmpConfig(newSetup.getAmpConfig());
        setup.setCab(newSetup.getCab());
        setup.setDescription(newSetup.getDescription());
        List<PedalSlot> newData = setup.getPedalboard() == null ? List.of() : new ArrayList<>(setup.getPedalboard());
        applyPedalboard(setup, userId, newData);
        setup.setGuitar(newSetup.getGuitar());
        return setupRepository.save(setup);
    }

    @Override
    public Setup getSetupById(Long setupId, Long userId) {
        return getSetup(setupId, userId);
    }

    @Override
    public List<Setup> getAllSetupsAdmin() {
        return setupRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteSetupAdmin(Long setupId) {
        setupRepository.deleteById(setupId);
    }

    private void applyPedalboard(Setup setup, Long userId, List<PedalSlot> newData) {
        if (setup.getPedalboard() == null) {
            setup.setPedalboard(new ArrayList<>());
        }
        setup.getPedalboard().clear();
        if (newData == null || newData.isEmpty()) {
            return;
        }
        for (int i = 0; i < newData.size(); i++) {
            PedalSlot newSlot = newData.get(i);
            Pedal pedal = pedalRepository.findByIdAndUser(newSlot.getPedal().getId(), userId).orElseThrow(() -> new ResourceNotFoundException("Педаль не найдена"));
            PedalSlot slot = new PedalSlot(
                    setup, pedal, i, pedal.getPedalType().getChainZone(), newSlot.getConfig()
            );
            setup.getPedalboard().add(slot);
        }
    }

    private Setup getSetup(Long id, Long userId) {
        return setupRepository.findByIdAndUser(id, userId).orElseThrow(() -> new ResourceNotFoundException("Пресет не найден"));
    }

}
