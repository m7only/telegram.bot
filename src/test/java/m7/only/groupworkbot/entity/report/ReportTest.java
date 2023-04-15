package m7.only.groupworkbot.entity.report;

import m7.only.groupworkbot.entity.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReportTest {
    public static final LocalDateTime CORRECT_LOCAL_DATE_TIME = LocalDateTime.now();
    public static final Long CORRECT_ID = 5147483648L;
    public static final String CORRECT_REPORT = "CORRECT REPORT";
    public static final LocalDateTime CORRECT_REPORT_DATE = LocalDateTime.now();
    public static final User CORRECT_USER = new User();

    public static final Set<ReportPhoto> CORRECT_REPORT_PHOTOS = new HashSet<>();


    private Report out;


    @Test
    void shouldCreateReportWithAllArgs() {
        out = new Report(CORRECT_ID,
                CORRECT_REPORT,
                CORRECT_REPORT_DATE,
                CORRECT_USER,
                CORRECT_REPORT_PHOTOS);

        CORRECT_REPORT_PHOTOS.add(new ReportPhoto("fileId_1", out));
        CORRECT_REPORT_PHOTOS.add(new ReportPhoto("fileId_2", out));
        CORRECT_REPORT_PHOTOS.add(new ReportPhoto("fileId_3", out));

        assertEquals(CORRECT_ID, out.getId());
        assertEquals(CORRECT_REPORT, out.getReport());
        assertEquals(CORRECT_REPORT_DATE, out.getReportDate());
        assertEquals(CORRECT_USER, out.getUser());
        assertEquals(CORRECT_REPORT_PHOTOS, out.getPhotos());
    }

    @Test
    void shouldCreateReportWithUserAndLocalDateTime() {
        out = new Report(CORRECT_USER, CORRECT_LOCAL_DATE_TIME);

        assertNull(out.getId());
        assertNull(out.getReport());
        assertEquals(CORRECT_LOCAL_DATE_TIME, out.getReportDate());
        assertEquals(CORRECT_USER, out.getUser());
        assertNull(out.getPhotos());
    }

    @Test
    void shouldSetAndGetId() {
        out = new Report();
        out.setId(CORRECT_ID);
        assertEquals(CORRECT_ID, out.getId());
    }

    @Test
    void shouldSetAndGetReport() {
        out = new Report();
        out.setReport(CORRECT_REPORT);
        assertEquals(CORRECT_REPORT, out.getReport());
    }

    @Test
    void shouldSetAndGetReportDate() {
        out = new Report();
        out.setReportDate(CORRECT_REPORT_DATE);
        assertEquals(CORRECT_REPORT_DATE, out.getReportDate());
    }

    @Test
    void shouldSetAndGetUser() {
        out = new Report();
        out.setUser(CORRECT_USER);
        assertEquals(CORRECT_USER, out.getUser());
    }

    @Test
    void shouldSetAndGetReportPhotos() {
        out = new Report();
        out.setPhotos(CORRECT_REPORT_PHOTOS);
        assertEquals(CORRECT_REPORT_PHOTOS, out.getPhotos());
    }

}