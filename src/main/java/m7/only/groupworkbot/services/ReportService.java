package m7.only.groupworkbot.services;

import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    Report saveReport(User user, String reportText, String fileId);

    Report findReportByUserAndCurrentDateOrCreateNew(User user);

    Report add(Report report);

    List<Report> getAll();

    Optional<Report> getById(Long id);

    Optional<Report> update(Long id, Report report);

    Optional<Report> delete(Long id);
}
