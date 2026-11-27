package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ksenia.NauJava.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(Long userId);
}
