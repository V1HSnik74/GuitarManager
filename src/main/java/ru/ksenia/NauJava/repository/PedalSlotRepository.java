package ru.ksenia.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ksenia.NauJava.entity.PedalSlot;

import java.util.List;

public interface PedalSlotRepository extends CrudRepository<PedalSlot, Long> {
    List<PedalSlot> findAllBySetupIdOrderByOrderIndexAsc(Long setupId);
    void deleteAllBySetupId(Long setupId);
}
