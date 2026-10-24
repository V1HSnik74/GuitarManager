package ru.ksenia.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ksenia.NauJava.entity.Cab;

import java.util.List;

@RepositoryRestResource(path = "cabs")
public interface CabRepository extends CrudRepository<Cab, Long> {
    List<Cab> findAllByUserId(Long userId);
    List<Cab> findAllBySpeakers(String speakers);
}
