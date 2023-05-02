package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.report.ReportPhoto;
import m7.only.groupworkbot.repository.ReportPhotoRepository;
import m7.only.groupworkbot.services.ReportPhotoService;
import m7.only.groupworkbot.services.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный класс для работы с сущностью {@link ReportPhoto}
 */
@Service
public class ReportPhotoServiceImpl implements ReportPhotoService {
    private final ReportPhotoRepository reportPhotoRepository;
    private final ReportService reportService;

    public ReportPhotoServiceImpl(ReportPhotoRepository reportPhotoRepository, ReportService reportService) {
        this.reportPhotoRepository = reportPhotoRepository;
        this.reportService = reportService;
    }

    /**
     * Сохранение новой фотографии через API.
     *
     * @param reportPhoto сохраняемая фотография
     * @return {@code ReportPhoto}
     */
    @Override
    public ReportPhoto add(ReportPhoto reportPhoto) {
        return reportPhotoRepository.save(reportPhoto);
    }

    /**
     * Получение списка всех фотографий для API.
     *
     * @return {@code List<ReportPhoto>}
     */
    @Override
    public List<ReportPhoto> getAll() {
        return reportPhotoRepository.findAll();
    }

    /**
     * Получение списка всех фотографий по отчету для API.
     *
     * @return {@code List<ReportPhoto>}
     */
    @Override
    public List<ReportPhoto> getAllByReportId(Long reportId) {
        return reportPhotoRepository.findAllByReport(reportService.getById(reportId).orElse(null));
    }

    /**
     * Получение фотографии отчета по id для API.
     *
     * @param id идентификатор фотографии отчета
     * @return {@code Optional<ReportPhoto>}, или {@code Optional.empty()}, если не найдена
     */
    @Override
    public Optional<ReportPhoto> getById(Long id) {
        return reportPhotoRepository.findById(id);
    }

    /**
     * Редактирование фотографии отчета по id для API. Проходит проверка на существование фотографии отчета.
     *
     * @param id          идентификатор фотографии отчета
     * @param reportPhoto тело редактируемого фотографии отчета
     * @return {@code Optional<ReportPhoto>} если изменена, или {@code Optional.empty()}, если не найдена
     */
    @Override
    public Optional<ReportPhoto> update(Long id, ReportPhoto reportPhoto) {
        return reportPhotoRepository.findById(id).isPresent()
                ? Optional.of(reportPhotoRepository.save(reportPhoto))
                : Optional.empty();
    }

    /**
     * Удаление фотографии отчета по id для API. Проходит проверка на существование фотографии отчета.
     *
     * @param id идентификатор фотографии отчета
     * @return {@code Optional<ReportPhoto>} если удалена, или {@code Optional.empty()}, если не найдена
     */
    @Override
    public Optional<ReportPhoto> delete(Long id) {
        Optional<ReportPhoto> optionalReportPhoto = reportPhotoRepository.findById(id);
        if (reportPhotoRepository.findById(id).isPresent()) {
            reportPhotoRepository.deleteById(id);
            return optionalReportPhoto;
        } else {
            return Optional.empty();
        }
    }
}
