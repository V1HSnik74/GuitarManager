package ru.ksenia.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ksenia.NauJava.entity.Report;

public interface ReportRepository extends CrudRepository<Report, Long> {
}
