package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.entity.User;

public interface UserService {
    void addNewUser(User user);
    User getUserByUsernameOrEmail(String username, String email);
}
