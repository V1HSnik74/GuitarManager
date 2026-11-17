package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ksenia.NauJava.entity.Cab;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "cabs")
public interface CabRepository extends JpaRepository<Cab, Long> {
    List<Cab> findAllByUserId(Long userId);
    Optional<Cab> findByIdAndUser(Long id, Long userId);
}
