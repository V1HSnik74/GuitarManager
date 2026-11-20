package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ksenia.NauJava.entity.Guitar;

import java.util.List;
import java.util.Optional;

public interface GuitarRepository extends JpaRepository<Guitar, Long> {
    List<Guitar> findAllByUserId(Long userId);
    Optional<Guitar> findByIdAndUser(Long id, Long userId);
}
