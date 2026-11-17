package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Guitar addGuitar(Guitar guitar, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователя не существует"));
        guitar.setUser(user);
        return guitarRepository.save(guitar);
    }

    @Override
    @Transactional
    public Guitar updateGuitar(Guitar newData, Long guitarId, Long userId) {
        Guitar guitar = getGuitar(guitarId, userId);
        guitar.setGuitarType(newData.getGuitarType());
        guitar.setPickupType(newData.getPickupType());
        guitar.setBrand(newData.getBrand());
        guitar.setModel(newData.getModel());
        guitar.setDescription(newData.getDescription());
        guitar.setStringsCount(newData.getStringsCount());
        return guitarRepository.save(guitar);
    }

    @Override
    @Transactional
    public void deleteGuitar(Long id, Long userId) {
        Guitar guitar = getGuitar(id, userId);
        throwIfUsed(id);
        guitarRepository.delete(guitar);
    }

    @Override
    public Guitar getGuitarById(Long id, Long userId) {
        return getGuitar(id, userId);
    }

    @Override
    public List<Guitar> getAllGuitarsByUserId(Long userId) {
        return guitarRepository.findAllByUserId(userId);
    }

    @Override
    public List<Guitar> getAllGuitarsAdmin() {
        return guitarRepository.findAll();
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
}
