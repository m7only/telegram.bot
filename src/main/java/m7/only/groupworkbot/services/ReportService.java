package m7.only.groupworkbot.services;

import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.user.User;

public interface ReportService {
    Report saveReport(User user, String reportText, String fileId);

    Report findReportByUserAndCurrentDateOrCreateNew(User user);
}
