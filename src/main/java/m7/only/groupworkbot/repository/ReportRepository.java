package m7.only.groupworkbot.repository;

import m7.only.groupworkbot.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий сущности {@linkplain Report Report}
 */
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByUserId(Long userId);

}
