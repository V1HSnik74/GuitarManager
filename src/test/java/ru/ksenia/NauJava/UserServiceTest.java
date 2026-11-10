package ru.ksenia.NauJava;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.enums.Role;
import ru.ksenia.NauJava.repository.UserRepository;
import ru.ksenia.NauJava.service.UserServiceImpl;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void addNewUserEncodesPassword() {
        User user = new User("test", "1234", "test", Role.USER);
        when(passwordEncoder.encode("1234")).thenReturn("hash123");
        userService.addNewUser(user);
        assertEquals("hash123", user.getPassword());
        verify(passwordEncoder).encode("1234");
    }

    @Test
    void addNewUserAssignsDefaultRole() {
        User user = new User("test", "1234", "test", null);
        when(passwordEncoder.encode(any())).thenReturn("hash123");
        userService.addNewUser(user);
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void addNewUserNotOverwritingRole() {
        User user = new User("admin", "12345", "admin", Role.ADMIN);
        when(passwordEncoder.encode(any())).thenReturn("hash123");
        userService.addNewUser(user);
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void addNewUserLoginDefault() {
        User user = new User(null, "1234", "test", Role.USER);
        when(passwordEncoder.encode(any())).thenReturn("hash123");
        userService.addNewUser(user);
        assertEquals("test", user.getLogin());
    }

    @Test
    void addNewUserSuccess() {
        User user = new User("test", "1234", "test", Role.USER);
        when(passwordEncoder.encode(any())).thenReturn("hash123");
        userService.addNewUser(user);
        verify(userRepository).save(user);
    }

    @Test
    void addNewUserPasswordEncoderThrowsException() {
        User user = new User("test", "1234", "test", Role.USER);
        when(passwordEncoder.encode(any())).thenThrow(new IllegalArgumentException("Невалидный пароль"));
        assertThrows(IllegalArgumentException.class, () -> userService.addNewUser(user));
        verify(userRepository, never()).save(any());
    }

    @Test
    void getUserByNameSuccess() {
        String username = "test";
        User user = new User("test", "1234", username, Role.USER);
        user.setId(1L);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        User result = userService.getUserByName(username);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(username, result.getUsername());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void getUserByNameNotFound() {
        String username = "doesnt_exist";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> userService.getUserByName(username));
        assertEquals("Пользователь не найден", e.getMessage());
        verify(userRepository).findByUsername(username);
    }
}
