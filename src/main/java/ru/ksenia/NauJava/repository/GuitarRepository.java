package ru.ksenia.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ksenia.NauJava.entity.Guitar;

import java.util.List;

@RepositoryRestResource(path = "guitars")
public interface GuitarRepository extends CrudRepository<Guitar, Long> {
    List<Guitar> findAllByUserId(Long userId);
    List<Guitar> findAllByPickupType(String pickupType);
    List<Guitar> findAllByBrand(String brand);
    List<Guitar> findAllByGuitarType(String guitarType);
}
