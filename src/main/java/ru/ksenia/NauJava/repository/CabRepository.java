package ru.ksenia.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ksenia.NauJava.entity.Cab;

import java.util.List;

public interface CabRepository extends CrudRepository<Cab, Long> {
    List<Cab> findAllByUserId(Long userId);
    List<Cab> findAllBySpeakers(String speakers);
}
