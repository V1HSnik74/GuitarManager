package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ksenia.NauJava.entity.Guitar;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "guitars")
public interface GuitarRepository extends JpaRepository<Guitar, Long> {
    List<Guitar> findAllByUserId(Long userId);
    Optional<Guitar> findByIdAndUser(Long id, Long userId);
}
