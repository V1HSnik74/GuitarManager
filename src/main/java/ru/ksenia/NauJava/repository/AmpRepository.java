package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ksenia.NauJava.entity.Amp;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "amps")
public interface AmpRepository extends JpaRepository<Amp, Long> {
    List<Amp> findAllByUserId(Long userId);
    Optional<Amp> findByIdAndUser(Long id, Long userId);
}
