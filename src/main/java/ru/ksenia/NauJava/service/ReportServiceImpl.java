package ru.ksenia.NauJava.service;

import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;
import ru.ksenia.NauJava.entity.Guitar;
import ru.ksenia.NauJava.entity.Report;
import ru.ksenia.NauJava.enums.ReportStatus;
import ru.ksenia.NauJava.repository.GuitarRepository;
import ru.ksenia.NauJava.repository.ReportRepository;
import ru.ksenia.NauJava.repository.UserRepository;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final GuitarRepository guitarRepository;

    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, GuitarRepository guitarRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.guitarRepository = guitarRepository;
    }

    @Override
    public String getReportContent(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new NoResultException("<p>Такого отчета нет</p>")).getContent();
    }

    @Override
    public Long createReport() {
        return reportRepository.save(new Report(ReportStatus.CREATED, "<p>Содержимое формируется</p>")).getId();
    }

    @Override
    public void formReport(Long id) {
        CompletableFuture.runAsync(() -> {
            long totalStartTime = System.currentTimeMillis();
            Report report = reportRepository.findById(id).orElseThrow(() -> new NoResultException("<p>Такого отчета нет</p>"));
            try {
                AtomicLong usersCount = new AtomicLong();
                AtomicLong usersElapsed = new AtomicLong();
                AtomicReference<Iterable<Guitar>> guitars = new AtomicReference<>();
                AtomicLong guitarsElapsed = new AtomicLong();
                Thread getUsersCount = new Thread(() -> {
                    long startTime = System.currentTimeMillis();
                    usersCount.set(userRepository.count());
                    usersElapsed.set(System.currentTimeMillis() - startTime);
                });
                Thread getGuitars = new Thread(() -> {
                    long startTime = System.currentTimeMillis();
                    guitars.set(guitarRepository.findAll());
                    guitarsElapsed.set(System.currentTimeMillis() - startTime);
                });
                getUsersCount.start();
                getGuitars.start();
                getUsersCount.join();
                getGuitars.join();
                StringBuilder guitarRows = new StringBuilder();
                if (guitars.get() != null) {
                    for (Guitar guitar : guitars.get()) {
                        guitarRows.append("<tr><td>")
                                .append(guitar.getId())
                                .append("</td><td>")
                                .append(guitar.getBrand())
                                .append("-").append(guitar.getModel())
                                .append("</td></tr>");
                    }
                }
                long totalElapsed = (System.currentTimeMillis() - totalStartTime);
                String content = """
                        <html lang="ru">
                        <body>
                        <h2>Отчет</h2>
                        <table border="1">
                            <tr><th>Параметр</th><th>Значение</th></tr>
                            <tr><td>Количество пользователей</td><td>%d</td></tr>
                            <tr><td>Время на подсчет пользователей</td><td>%d мс</td></tr>
                            <tr><td>Время на получение гитар</td><td>%d мс</td></tr>
                            <tr><td>Общее время формирования</td><td>%d мс</td></tr>
                        </table>
                        <h3>Список гитар</h3>
                        <table border="1">
                            <tr><th>ID</th><th>Название</th></tr>
                            %s
                        </table>
                        </body>
                        </html>""".formatted(usersCount.get(), usersElapsed.get(), guitarsElapsed.get(), totalElapsed, guitarRows.toString());
                report.setContent(content);
                report.setStatus(ReportStatus.COMPLETED);
                reportRepository.save(report);
            } catch (Exception e) {
                report.setStatus(ReportStatus.ERROR);
                report.setContent("<p>Ошибка формирования отчета</p>");
                reportRepository.save(report);
            }
        });
    }
}
