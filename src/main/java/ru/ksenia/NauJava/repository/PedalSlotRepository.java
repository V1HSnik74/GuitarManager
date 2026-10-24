package ru.ksenia.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ksenia.NauJava.entity.PedalSlot;

import java.util.List;

@RepositoryRestResource(path = "pedal_slots")
public interface PedalSlotRepository extends CrudRepository<PedalSlot, Long> {
    List<PedalSlot> findAllBySetupIdOrderByOrderIndexAsc(Long setupId);
    void deleteAllBySetupId(Long setupId);
}
