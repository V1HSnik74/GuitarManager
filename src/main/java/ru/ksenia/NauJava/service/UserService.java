package ru.ksenia.NauJava.service;

import ru.ksenia.NauJava.entity.User;

public interface UserService {
    void addNewUser(User user);
    User getUserByName(String username);
}
