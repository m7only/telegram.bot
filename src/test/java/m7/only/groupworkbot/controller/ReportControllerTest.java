package m7.only.groupworkbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.services.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    private static final Long CORRECT_REPORT_ID = 17L;
    private static final String CORRECT_REPORT_TEXT = "report text";
    private static final LocalDateTime CORRECT_LOCAL_DATE_TIME = LocalDateTime.now();
    private static final Report CORRECT_REPORT = new Report(
            CORRECT_REPORT_ID,
            CORRECT_REPORT_TEXT,
            CORRECT_LOCAL_DATE_TIME,
            new User(),
            new HashSet<>()
    );
    private static final List<Report> CORRECT_REPORT_LIST = List.of(CORRECT_REPORT);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReportService reportService;

    @Test
    public void shouldReturnCreatedAnimalShelter() throws Exception {
        when(reportService.add(any())).thenReturn(CORRECT_REPORT);
        String json = objectMapper.writeValueAsString(CORRECT_REPORT);
        mockMvc.perform(
                        post("/report")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(reportService).add(any());
    }

    @Test
    public void shouldReturnAnimalSheltersList() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_REPORT_LIST);
        when(reportService.getAll()).thenReturn(CORRECT_REPORT_LIST);
        mockMvc.perform(
                        get("/report"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(reportService).getAll();
    }

    @Test
    public void shouldReturnCorrectAnimalShelterById() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_REPORT);
        when(reportService.getById(any())).thenReturn(Optional.of(CORRECT_REPORT));
        mockMvc.perform(
                        get("/report/" + CORRECT_REPORT_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(reportService).getById(any());
    }

    @Test
    public void shouldReturnEditedByIdCorrectAnimalShelter() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_REPORT);
        when(reportService.update(any(), any())).thenReturn(Optional.of(CORRECT_REPORT));
        mockMvc.perform(
                        put("/report/" + CORRECT_REPORT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_REPORT_ID.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(reportService).update(any(), any());
    }

    @Test
    public void shouldReturnDeletedByIdCorrectAnimalShelter() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_REPORT);
        when(reportService.delete(any())).thenReturn(Optional.of(CORRECT_REPORT));
        mockMvc.perform(
                        delete("/report/" + CORRECT_REPORT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_REPORT_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(reportService).delete(any());
    }
}