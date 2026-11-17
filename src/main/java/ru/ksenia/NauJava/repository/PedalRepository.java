package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ksenia.NauJava.entity.Pedal;
import ru.ksenia.NauJava.enums.PedalTypes;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "pedals")
public interface PedalRepository extends JpaRepository<Pedal, Long> {
    List<Pedal> findAllByUserId(Long userId);
    List<Pedal> findAllByPedalType(PedalTypes pedalTypes);
    @Query("SELECT ps.pedal FROM PedalSlot ps GROUP BY ps.pedal ORDER BY COUNT(ps) DESC")
    List<Pedal> findMostPopularPedals();
    Optional<Pedal> findByIdAndUser(Long id, Long userId);
}
