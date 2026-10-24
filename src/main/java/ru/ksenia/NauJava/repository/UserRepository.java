package ru.ksenia.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ksenia.NauJava.entity.User;

import java.util.Optional;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByLogin(String login);
    boolean existsByUsername(String username);
    Optional<User> findByLoginOrUsername(String login, String username);
}
