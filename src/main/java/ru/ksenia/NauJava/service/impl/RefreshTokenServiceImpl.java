package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ksenia.NauJava.entity.RefreshToken;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.exception.RefreshTokenException;
import ru.ksenia.NauJava.repository.RefreshTokenRepository;
import ru.ksenia.NauJava.service.interfaces.RefreshTokenService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;


@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshExpiration;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,
                                   @Value("${jwt.refresh-expiration:2592000000}") long refreshExpiration) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshExpiration = refreshExpiration;
    }

    @Override
    @Transactional
    public RefreshToken create(User user) {
        String raw = UUID.randomUUID().toString();
        RefreshToken token = new RefreshToken();
        token.setToken(hash(raw));
        token.setRawToken(raw);
        token.setUser(user);
        token.setExpiresAt(Instant.now().plusMillis(refreshExpiration));
        return refreshTokenRepository.save(token);
    }

    @Override
    @Transactional(noRollbackFor = RefreshTokenException.class)
    public RefreshToken rotate(String token) {
        RefreshToken oldToken = refreshTokenRepository.findByToken(hash(token))
                .orElseThrow(() -> new RefreshTokenException("REFRESH_TOKEN_INVALID", "Refresh-токен не найден"));
        if (oldToken.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(oldToken);
            throw new RefreshTokenException("REFRESH_TOKEN_EXPIRED", "Срок действия refresh-токена истёк");
        }
        refreshTokenRepository.delete(oldToken);
        return create(oldToken.getUser());
    }

    @Override
    @Transactional
    public void revoke(String token) {
        refreshTokenRepository.findByToken(hash(token)).ifPresent(refreshTokenRepository::delete);
    }

    @Override
    @Transactional
    public void revokeAllForUser(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    private String hash(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(token.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
