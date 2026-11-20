package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ksenia.NauJava.dto.CabRequest;
import ru.ksenia.NauJava.dto.CabResponse;
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
    public CabResponse addCab(CabRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        Cab cab = new Cab();
        cab.setUser(user);
        applyFields(cab, request);
        return CabResponse.fromEntity(cabRepository.save(cab));
    }

    @Override
    @Transactional
    public CabResponse updateCab(CabRequest newData, Long id, Long userId) {
        Cab cab = getCab(id, userId);
        applyFields(cab, newData);
        return CabResponse.fromEntity(cabRepository.save(cab));
    }

    @Override
    @Transactional
    public void deleteCab(Long id, Long userId) {
        Cab cab = getCab(id, userId);
        throwIfUsed(id);
        cabRepository.delete(cab);
    }

    @Override
    public CabResponse getCabById(Long id, Long userId) {
        return CabResponse.fromEntity(getCab(id, userId));
    }

    @Override
    public List<CabResponse> getAllCabsByUser(Long userId) {
        return cabRepository.findAllByUserId(userId).stream()
                .map(CabResponse::fromEntity)
                .toList();
    }

    @Override
    public List<CabResponse> getAllCabsAdmin() {
        return cabRepository.findAll().stream()
                .map(CabResponse::fromEntity)
                .toList();
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

    private void applyFields(Cab cab, CabRequest data) {
        cab.setBrand(data.brand());
        cab.setModel(data.model());
        cab.setSpeakers(data.speakers());
        cab.setDescription(data.description());
    }
}
