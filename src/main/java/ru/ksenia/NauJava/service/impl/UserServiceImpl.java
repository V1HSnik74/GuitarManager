package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ksenia.NauJava.dto.RegisterRequest;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.enums.Role;
import ru.ksenia.NauJava.repository.UserRepository;
import ru.ksenia.NauJava.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addNewUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Пользователь с такой почтой уже существует");
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Это имя пользователя уже занято");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user.setEmail(request.email());
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsernameOrEmail(String username, String email) {
        return userRepository.findByEmailOrUsername(email, username).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
    }


}
