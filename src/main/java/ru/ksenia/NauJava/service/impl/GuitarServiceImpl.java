package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ksenia.NauJava.dto.GuitarRequest;
import ru.ksenia.NauJava.dto.GuitarResponse;
import ru.ksenia.NauJava.entity.Guitar;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.repository.GuitarRepository;
import ru.ksenia.NauJava.repository.SetupRepository;
import ru.ksenia.NauJava.repository.UserRepository;
import ru.ksenia.NauJava.service.interfaces.GuitarService;

import java.util.List;

@Service
public class GuitarServiceImpl implements GuitarService {
    private final GuitarRepository guitarRepository;
    private final UserRepository userRepository;
    private final SetupRepository setupRepository;

    @Autowired
    public GuitarServiceImpl(GuitarRepository guitarRepository, UserRepository userRepository, SetupRepository setupRepository) {
        this.guitarRepository = guitarRepository;
        this.userRepository = userRepository;
        this.setupRepository = setupRepository;
    }

    @Override
    @Transactional
    public GuitarResponse addGuitar(GuitarRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователя не существует"));
        Guitar guitar = new Guitar();
        guitar.setUser(user);
        applyFields(guitar, request);
        return GuitarResponse.fromEntity(guitarRepository.save(guitar));
    }

    @Override
    @Transactional
    public GuitarResponse updateGuitar(GuitarRequest newData, Long guitarId, Long userId) {
        Guitar guitar = getGuitar(guitarId, userId);
        applyFields(guitar, newData);
        return GuitarResponse.fromEntity(guitarRepository.save(guitar));
    }

    @Override
    @Transactional
    public void deleteGuitar(Long id, Long userId) {
        Guitar guitar = getGuitar(id, userId);
        throwIfUsed(id);
        guitarRepository.delete(guitar);
    }

    @Override
    public GuitarResponse getGuitarById(Long id, Long userId) {
        return GuitarResponse.fromEntity(getGuitar(id, userId));
    }

    @Override
    public List<GuitarResponse> getAllGuitarsByUserId(Long userId) {
        return guitarRepository.findAllByUserId(userId).stream()
                .map(GuitarResponse::fromEntity)
                .toList();
    }

    @Override
    public List<GuitarResponse> getAllGuitarsAdmin() {
        return guitarRepository.findAll().stream()
                .map(GuitarResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public void deleteGuitarAdmin(Long guitarId) {
        throwIfUsed(guitarId);
        guitarRepository.deleteById(guitarId);
    }

    private Guitar getGuitar(Long id, Long userId) {
        return guitarRepository.findByIdAndUser(id, userId).orElseThrow(() -> new ResourceNotFoundException("Гитара не найдена"));
    }

    private void throwIfUsed(Long guitarId) {
        if (setupRepository.existsByGuitarId(guitarId)) {
            throw new IllegalStateException("Нельзя удалить гитару, так как она используется в пресетах");
        }
    }

    private void applyFields(Guitar guitar, GuitarRequest data) {
        guitar.setBrand(data.brand());
        guitar.setModel(data.model());
        guitar.setStringsCount(data.stringsCount() == 0 ? 6 : data.stringsCount());
        guitar.setPickupType(data.pickupType());
        guitar.setGuitarType(data.guitarType());
        guitar.setDescription(data.description());
    }
}
