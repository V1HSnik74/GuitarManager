package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ksenia.NauJava.entity.Amp;

import java.util.List;
import java.util.Optional;

public interface AmpRepository extends JpaRepository<Amp, Long> {
    List<Amp> findAllByUserId(Long userId);
    Optional<Amp> findByIdAndUserId(Long id, Long userId);
}
