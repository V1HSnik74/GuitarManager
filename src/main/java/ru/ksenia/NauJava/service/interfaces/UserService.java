package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.dto.RegisterRequest;
import ru.ksenia.NauJava.entity.User;

public interface UserService {
    User addNewUser(RegisterRequest request);
    User getUserByUsernameOrEmail(String username, String email);
}
