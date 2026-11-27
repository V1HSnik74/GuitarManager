package ru.ksenia.NauJava.service.interfaces;

import ru.ksenia.NauJava.entity.RefreshToken;
import ru.ksenia.NauJava.entity.User;

public interface RefreshTokenService {
    RefreshToken create(User user);
    RefreshToken rotate(String token);
    void revoke(String token);
    void revokeAllForUser(Long userId);
}
