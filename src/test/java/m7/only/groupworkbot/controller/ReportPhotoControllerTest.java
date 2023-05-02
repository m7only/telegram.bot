package m7.only.groupworkbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.report.ReportPhoto;
import m7.only.groupworkbot.services.ReportPhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportPhotoController.class)
public class ReportPhotoControllerTest {
    private static final Long CORRECT_REPORT_PHOTO_ID = 17L;
    private static final Long CORRECT_REPORT_ID = 24L;
    private static final String CORRECT_REPORT_PHOTO_FILE_ID = "564646";

    private static final ReportPhoto CORRECT_REPORT_PHOTO = new ReportPhoto(
            CORRECT_REPORT_PHOTO_ID,
            CORRECT_REPORT_PHOTO_FILE_ID,
            new Report()
    );
    private static final List<ReportPhoto> CORRECT_REPORT_PHOTO_LIST = List.of(CORRECT_REPORT_PHOTO);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReportPhotoService reportPhotoService;

    @Test
    public void shouldReturnCreatedAnimalShelter() throws Exception {
        when(reportPhotoService.add(any())).thenReturn(CORRECT_REPORT_PHOTO);
        String json = objectMapper.writeValueAsString(CORRECT_REPORT_PHOTO);
        mockMvc.perform(
                        post("/reportPhoto")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(reportPhotoService).add(any());
    }

    @Test
    public void shouldReturnAnimalSheltersList() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_REPORT_PHOTO_LIST);
        when(reportPhotoService.getAll()).thenReturn(CORRECT_REPORT_PHOTO_LIST);
        mockMvc.perform(
                        get("/reportPhoto"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(reportPhotoService).getAll();
    }

    @Test
    public void shouldReturnAnimalSheltersListByReportId() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_REPORT_PHOTO_LIST);
        when(reportPhotoService.getAllByReportId(any())).thenReturn(CORRECT_REPORT_PHOTO_LIST);
        mockMvc.perform(
                        get("/reportPhoto/report/" + CORRECT_REPORT_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(reportPhotoService).getAllByReportId(any());
    }

    @Test
    public void shouldReturnCorrectAnimalShelterById() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_REPORT_PHOTO);
        when(reportPhotoService.getById(any())).thenReturn(Optional.of(CORRECT_REPORT_PHOTO));
        mockMvc.perform(
                        get("/reportPhoto/" + CORRECT_REPORT_PHOTO_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(reportPhotoService).getById(any());
    }

    @Test
    public void shouldReturnEditedByIdCorrectAnimalShelter() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_REPORT_PHOTO);
        when(reportPhotoService.update(any(), any())).thenReturn(Optional.of(CORRECT_REPORT_PHOTO));
        mockMvc.perform(
                        put("/reportPhoto/" + CORRECT_REPORT_PHOTO_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_REPORT_PHOTO_ID.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(reportPhotoService).update(any(), any());
    }

    @Test
    public void shouldReturnDeletedByIdCorrectAnimalShelter() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_REPORT_PHOTO);
        when(reportPhotoService.delete(any())).thenReturn(Optional.of(CORRECT_REPORT_PHOTO));
        mockMvc.perform(
                        delete("/reportPhoto/" + CORRECT_REPORT_PHOTO_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_REPORT_PHOTO_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(reportPhotoService).delete(any());
    }
}