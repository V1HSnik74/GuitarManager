package ru.ksenia.NauJava.service.interfaces;

public interface ReportService {
    String getReportContent(Long id);
    Long createReport();
    void formReport(Long id);
}
