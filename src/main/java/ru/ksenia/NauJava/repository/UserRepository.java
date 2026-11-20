package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ksenia.NauJava.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByEmailOrUsername(String email, String username);
    Optional <User> findByUsername(String username);
}
