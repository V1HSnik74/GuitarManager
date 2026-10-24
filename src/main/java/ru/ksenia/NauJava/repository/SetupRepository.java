package ru.ksenia.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.ksenia.NauJava.entity.Setup;
import ru.ksenia.NauJava.enums.PedalTypes;

import java.util.List;

@RepositoryRestResource(path = "setups")
public interface SetupRepository extends CrudRepository<Setup, Long> {
    List<Setup> findAllByUserId(Long userId);

    @Query("SELECT DISTINCT s FROM Setup s JOIN s.pedalboard slot WHERE slot.pedal.pedalType = :pedalType" )
    List<Setup> findAllContainingPedalType(@Param("pedalType")PedalTypes pedalType);

    boolean existsByUserIdAndName(Long userId, String name);

    @Query("SELECT s FROM Setup s WHERE s.amp.brand = :brand AND s.amp.model = :model")
    List<Setup> findAllByAmpBrandAndModel(@Param("brand") String brand, @Param("model") String model);

    @Query("SELECT s FROM Setup s WHERE s.guitar.brand = :brand AND s.guitar.model = :model")
    List<Setup> findAllByGuitarModelAndBrand(@Param("brand") String brand, @Param("model") String model);
}
