package m7.only.groupworkbot.entity.report;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportPhotoTest {
    public static final Long CORRECT_ID = 5145483347L;
    public static final String CORRECT_FILE_ID = "45646";
    public static final Report CORRECT_REPORT = new Report();

    private ReportPhoto out;

    @Test
    void shouldSetIdAndGetId() {
        out = new ReportPhoto();
        out.setId(CORRECT_ID);
        assertEquals(CORRECT_ID, out.getId());
    }

    @Test
    void shouldSetAndGetFileId() {
        out = new ReportPhoto();
        out.setFileId(CORRECT_FILE_ID);
        assertEquals(CORRECT_FILE_ID, out.getFileId());
    }

    @Test
    void shouldSetAndGetReport() {
        out = new ReportPhoto();
        out.setReport(CORRECT_REPORT);
        assertEquals(CORRECT_REPORT, out.getReport());
    }

    @Test
    void shouldCreateReportPhotoWithAllArgs() {
        out = new ReportPhoto(CORRECT_ID, CORRECT_FILE_ID, CORRECT_REPORT);
        assertEquals(CORRECT_ID, out.getId());
        assertEquals(CORRECT_FILE_ID, out.getFileId());
        assertEquals(CORRECT_REPORT, out.getReport());
    }

    @Test
    void shouldCreateReportPhotoWithFileIdAndReport() {
        out = new ReportPhoto(CORRECT_FILE_ID, CORRECT_REPORT);
        assertEquals(CORRECT_FILE_ID, out.getFileId());
        assertEquals(CORRECT_REPORT, out.getReport());
    }
}