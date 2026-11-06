package ru.ksenia.NauJava.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ksenia.NauJava.service.ReportService;

import java.io.IOException;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public Long createReport(HttpServletResponse response) throws IOException {
        Long id = reportService.createReport();
        reportService.formReport(id);
        response.sendRedirect("/report/"+id);
        return id;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE, value = "/{id}")
    public String showContent(@PathVariable Long id) {
        return reportService.getReportContent(id);
    }
}
