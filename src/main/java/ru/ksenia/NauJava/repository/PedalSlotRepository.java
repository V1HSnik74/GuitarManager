package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ksenia.NauJava.entity.PedalSlot;

import java.util.List;

@RepositoryRestResource(path = "pedal_slots")
public interface PedalSlotRepository extends JpaRepository<PedalSlot, Long> {
    List<PedalSlot> findAllBySetupIdOrderByOrderIndexAsc(Long setupId, Long userId);
    boolean existsByPedalId(Long pedalId);
}
