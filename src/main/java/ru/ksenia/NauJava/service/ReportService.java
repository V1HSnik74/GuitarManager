package ru.ksenia.NauJava.service;

public interface ReportService {
    String getReportContent(Long id);
    Long createReport();
    void formReport(Long id);
}
