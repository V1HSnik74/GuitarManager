package ru.ksenia.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ksenia.NauJava.entity.Guitar;

import java.util.List;

public interface GuitarRepository extends CrudRepository<Guitar, Long> {
    List<Guitar> findAllByUserId(Long userId);
    List<Guitar> findAllByPickupType(String pickupType);
    List<Guitar> findAllByBrand(String brand);
    List<Guitar> findAllByGuitarType(String guitarType);
}
