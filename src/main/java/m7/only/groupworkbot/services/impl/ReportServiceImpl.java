package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.report.ReportPhoto;
import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.repository.ReportRepository;
import m7.only.groupworkbot.services.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Сервисный класс для работы с сущносьтю {@link Report}
 */
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Сохранение или обновление отчета. Происходит поиск отчета по пользователю, если нет - создается новый отчет.
     * Если есть новый текст отчета, обновляем его. Если есть новое фото, сохраняем ее.
     *
     * @param user       пользователь
     * @param reportText текст отчета
     * @param fileId     {@code telegram} идентификатор файла
     * @return {@code Report} отчет
     */
    @Override
    public Report saveReport(User user, String reportText, String fileId) {
        Report report = findReportByUserAndCurrentDateOrCreateNew(user);

        if (reportText != null && !reportText.isBlank()) {
            report.setReport(reportText);
        }

        if (fileId != null && !fileId.isBlank()) {
            if (report.getPhotos() == null) {
                report.setPhotos(new HashSet<>());
            }
            report.getPhotos().add(new ReportPhoto(fileId, report));
        }
        return reportRepository.save(report);
    }

    /**
     * Поиск отчета на текущую дату, если отсутствует - вернет новый.
     *
     * @param user пользователь
     * @return {@code Report} отчет
     */
    @Override
    public Report findReportByUserAndCurrentDateOrCreateNew(User user) {
        List<Report> reports = reportRepository.findAllByUserId(user.getId());
        Optional<Report> optionalReport = reports.stream()
                .filter(report -> report.getReportDate()
                        .toLocalDate()
                        .isEqual(LocalDate.now()))
                .findAny();
        return optionalReport.orElseGet(() ->
                new Report(
                        user,
                        LocalDateTime.now())
        );
    }
}
