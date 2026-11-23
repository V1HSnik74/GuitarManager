package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ksenia.NauJava.entity.PedalSlot;

import java.util.List;

public interface PedalSlotRepository extends JpaRepository<PedalSlot, Long> {
    List<PedalSlot> findAllBySetupIdAndSetupUserIdOrderByOrderIndexAsc(Long setupId, Long userId);
    boolean existsByPedalId(Long pedalId);
}
