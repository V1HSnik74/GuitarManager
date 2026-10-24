package ru.ksenia.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ksenia.NauJava.entity.Amp;
import ru.ksenia.NauJava.enums.AmpTypes;

import java.util.List;

@RepositoryRestResource(path = "amps")
public interface AmpRepository extends CrudRepository<Amp, Long> {
    List<Amp> findAllByUserId(Long userId);
    List<Amp> findAllByAmpType(AmpTypes ampType);
}
