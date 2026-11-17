package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ksenia.NauJava.entity.Cab;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.repository.CabRepository;
import ru.ksenia.NauJava.repository.SetupRepository;
import ru.ksenia.NauJava.repository.UserRepository;
import ru.ksenia.NauJava.service.interfaces.CabService;

import java.util.List;

@Service
public class CabServiceImpl implements CabService {
    private final UserRepository userRepository;
    private final CabRepository cabRepository;
    private final SetupRepository setupRepository;

    @Autowired
    public CabServiceImpl(UserRepository userRepository, CabRepository cabRepository, SetupRepository setupRepository) {
        this.userRepository = userRepository;
        this.cabRepository = cabRepository;
        this.setupRepository = setupRepository;
    }

    @Override
    @Transactional
    public Cab addCab(Cab cab, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        cab.setUser(user);
        return cabRepository.save(cab);
    }

    @Override
    @Transactional
    public Cab updateCab(Cab newData, Long id, Long userId) {
        Cab cab = getCab(id, userId);
        cab.setBrand(newData.getBrand());
        cab.setDescription(newData.getDescription());
        cab.setModel(newData.getModel());
        cab.setSpeakers(newData.getSpeakers());
        return cabRepository.save(cab);
    }

    @Override
    @Transactional
    public void deleteCab(Long id, Long userId) {
        Cab cab = getCab(id, userId);
        throwIfUsed(id);
        cabRepository.delete(cab);
    }

    @Override
    public Cab getCabById(Long id, Long userId) {
        return getCab(id, userId);
    }

    @Override
    public List<Cab> getAllCabsByUser(Long userId) {
        return cabRepository.findAllByUserId(userId);
    }

    @Override
    public List<Cab> getAllCabsAdmin() {
        return cabRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteCabAdmin(Long id) {
        throwIfUsed(id);
        cabRepository.deleteById(id);
    }

    private Cab getCab(Long id, Long userId) {
        return cabRepository.findByIdAndUser(id, userId).orElseThrow(() -> new ResourceNotFoundException("Кабинет не найден"));
    }

    private void throwIfUsed(Long id) {
        if (setupRepository.existsByCabId(id)) {
            throw new IllegalStateException("Нельзя удалить кабинет, так как он привязан к пресетам");
        }
    }
}
