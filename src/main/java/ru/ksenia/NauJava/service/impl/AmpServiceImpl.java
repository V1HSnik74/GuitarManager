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
import ru.ksenia.NauJava.service.interfaces.CloudinaryService;

import java.util.List;

@Service
public class AmpServiceImpl implements AmpService {
    private final AmpRepository ampRepository;
    private final SetupRepository setupRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public AmpServiceImpl(AmpRepository ampRepository, SetupRepository setupRepository, UserRepository userRepository, CloudinaryService cloudinaryService) {
        this.ampRepository = ampRepository;
        this.setupRepository = setupRepository;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
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
        String oldImage = amp.getImageUrl();
        applyFields(amp, newData);
        String newImage = amp.getImageUrl();
        AmpResponse updAmp = AmpResponse.fromEntity(ampRepository.save(amp));
        if (oldImage != null && !oldImage.equals(newImage)) {
            cloudinaryService.deleteImageByUrl(oldImage);
        }
        return updAmp;
    }

    @Override
    @Transactional
    public void deleteAmp(Long id, Long userId) {
        Amp amp = getAmp(id, userId);
        throwIfExists(id);
        String image = amp.getImageUrl();
        ampRepository.delete(amp);
        cloudinaryService.deleteImageByUrl(image);
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
        Amp amp = ampRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Усилитель не найден"));
        throwIfExists(id);
        String image = amp.getImageUrl();
        ampRepository.delete(amp);
        cloudinaryService.deleteImageByUrl(image);
    }

    private Amp getAmp(Long id, Long userId) {
        return ampRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Усилитель не найден"));
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
        amp.setImageUrl(data.imageUrl());
    }
}
