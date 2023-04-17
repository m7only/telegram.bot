package m7.only.groupworkbot.repository;

import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.report.ReportPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий сущности {@linkplain ReportPhoto ReportPhoto}
 */
public interface ReportPhotoRepository extends JpaRepository<ReportPhoto, Long> {
    List<ReportPhoto> findAllByReport(Report report);
}
