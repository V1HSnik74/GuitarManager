package ru.ksenia.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ksenia.NauJava.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByLogin(String login);
    boolean existsByUsername(String username);
    Optional<User> findByLoginOrUsername(String login, String username);
}
