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

    /**
     * Сохранение нового отчета через API.
     *
     * @param report сохраняемый отчет
     * @return {@code Report}
     */
    @Override
    public Report add(Report report) {
        return reportRepository.save(report);

    }

    /**
     * Получение списка всех отчетов для API.
     *
     * @return {@code List<Report>}
     */
    @Override
    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    /**
     * Получение отчета по id для API.
     *
     * @param id идентификатор отчета
     * @return {@code Optional<Report>}, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<Report> getById(Long id) {
        return reportRepository.findById(id);
    }

    /**
     * Редактирование отчета по id для API. Проходит проверка на существование отчета.
     *
     * @param id     идентификатор отчета
     * @param report тело редактируемого отчета
     * @return {@code Optional<Report>} если изменен, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<Report> update(Long id, Report report) {
        return reportRepository.findById(id).isPresent()
                ? Optional.of(reportRepository.save(report))
                : Optional.empty();
    }

    /**
     * Удаление отчета по id для API. Проходит проверка на существование отчета.
     *
     * @param id идентификатор отчета
     * @return {@code Optional<Report>} если удален, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<Report> delete(Long id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (reportRepository.findById(id).isPresent()) {
            reportRepository.deleteById(id);
            return optionalReport;
        } else {
            return Optional.empty();
        }
    }
}
