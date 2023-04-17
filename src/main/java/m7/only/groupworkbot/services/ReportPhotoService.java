package m7.only.groupworkbot.services;

import m7.only.groupworkbot.entity.report.ReportPhoto;

import java.util.List;
import java.util.Optional;

public interface ReportPhotoService {
    ReportPhoto add(ReportPhoto reportPhoto);

    List<ReportPhoto> getAll();

    List<ReportPhoto> getAllByReportId(Long reportId);

    Optional<ReportPhoto> getById(Long id);

    Optional<ReportPhoto> update(Long id, ReportPhoto reportPhoto);

    Optional<ReportPhoto> delete(Long id);
}
