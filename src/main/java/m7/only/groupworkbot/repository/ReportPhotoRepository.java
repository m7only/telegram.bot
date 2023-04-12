package m7.only.groupworkbot.repository;

import m7.only.groupworkbot.entity.report.ReportPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий сущности {@linkplain ReportPhoto ReportPhoto}
 */
public interface ReportPhotoRepository extends JpaRepository<ReportPhoto, Long> {
}
