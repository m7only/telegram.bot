package m7.only.groupworkbot.repository;

import m7.only.groupworkbot.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
