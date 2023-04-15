package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {
    public static final User CORRECT_USER = new User(1564641614646L);
    public static final LocalDateTime CURRENT_LOCAL_DATE_TIME = LocalDateTime.now();
    public static final Report CURRENT_DAY_REPORT = new Report(CORRECT_USER, CURRENT_LOCAL_DATE_TIME);
    public static final Report LAST_MONTH_DAY_REPORT = new Report(CORRECT_USER, CURRENT_LOCAL_DATE_TIME.minusMonths(1));
    public static final String CORRECT_FILE_ID = "51616151688";
    public static final String CORRECT_REPORT_TEXT = "report text";

    @Mock
    private ReportRepository reportRepositoryMock;

    @InjectMocks
    private ReportServiceImpl out;

    @Test
    void shouldReturnReportWithAllArgs() {
        out.saveReport(CORRECT_USER, CORRECT_REPORT_TEXT, CORRECT_FILE_ID);
        ArgumentCaptor<Report> argumentCaptor = ArgumentCaptor.forClass(Report.class);
        verify(reportRepositoryMock).save(argumentCaptor.capture());
        Report capturedReport = argumentCaptor.getValue();
        assertEquals(CORRECT_USER, capturedReport.getUser());
        assertEquals(CORRECT_REPORT_TEXT, capturedReport.getReport());
        assertTrue(capturedReport
                .getPhotos()
                .stream()
                .anyMatch(reportPhoto ->
                        reportPhoto
                                .getFileId()
                                .equals(CORRECT_FILE_ID))
        );
    }

    @Test
    void shouldReturnCurrentDayReport() {
        when(reportRepositoryMock.findAllByUserId(any())).thenReturn(List.of(CURRENT_DAY_REPORT));
        assertEquals(CURRENT_DAY_REPORT, out.findReportByUserAndCurrentDateOrCreateNew(new User()));
    }

    @Test
    void shouldReturnNewReport() {
        when(reportRepositoryMock.findAllByUserId(any())).thenReturn(List.of(LAST_MONTH_DAY_REPORT));
        assertEquals(CURRENT_LOCAL_DATE_TIME.toLocalDate(),
                out.findReportByUserAndCurrentDateOrCreateNew(CORRECT_USER)
                        .getReportDate()
                        .toLocalDate()
        );
        assertEquals(CORRECT_USER, out.findReportByUserAndCurrentDateOrCreateNew(CORRECT_USER).getUser());
    }
}