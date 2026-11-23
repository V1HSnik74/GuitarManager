package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ksenia.NauJava.entity.Cab;

import java.util.List;
import java.util.Optional;

public interface CabRepository extends JpaRepository<Cab, Long> {
    List<Cab> findAllByUserId(Long userId);
    Optional<Cab> findByIdAndUserId(Long id, Long userId);
}
