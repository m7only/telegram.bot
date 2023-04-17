package m7.only.groupworkbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import m7.only.groupworkbot.entity.user.Volunteer;
import m7.only.groupworkbot.services.VolunteerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VolunteerController.class)
public class VolunteerControllerTest {
    public static final String CORRECT_FULL_NAME = "Чннгизов Имгабур Абрамбабаевич";
    public static final String CORRECT_PHONE = "4564551";
    public static final Long CORRECT_CHAT_ID = 6147483647L;
    private static final Long CORRECT_VOLUNTEER_ID = 17L;
    private static final Volunteer CORRECT_VOLUNTEER = new Volunteer(
            CORRECT_VOLUNTEER_ID,
            CORRECT_FULL_NAME,
            CORRECT_PHONE,
            CORRECT_CHAT_ID,
            new HashSet<>()
    );
    private static final List<Volunteer> CORRECT_VOLUNTEER_LIST = List.of(CORRECT_VOLUNTEER);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VolunteerService volunteerService;

    @Test
    public void shouldReturnCreatedAnimalShelter() throws Exception {
        when(volunteerService.add(any())).thenReturn(CORRECT_VOLUNTEER);
        String json = objectMapper.writeValueAsString(CORRECT_VOLUNTEER);
        mockMvc.perform(
                        post("/volunteer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(volunteerService).add(any());
    }

    @Test
    public void shouldReturnAnimalSheltersList() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_VOLUNTEER_LIST);
        when(volunteerService.getAll()).thenReturn(CORRECT_VOLUNTEER_LIST);
        mockMvc.perform(
                        get("/volunteer"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(volunteerService).getAll();
    }

    @Test
    public void shouldReturnCorrectAnimalShelterById() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_VOLUNTEER);
        when(volunteerService.getById(any())).thenReturn(Optional.of(CORRECT_VOLUNTEER));
        mockMvc.perform(
                        get("/volunteer/" + CORRECT_VOLUNTEER_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(volunteerService).getById(any());
    }

    @Test
    public void shouldReturnEditedByIdCorrectAnimalShelter() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_VOLUNTEER);
        when(volunteerService.update(any(), any())).thenReturn(Optional.of(CORRECT_VOLUNTEER));
        mockMvc.perform(
                        put("/volunteer/" + CORRECT_VOLUNTEER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_VOLUNTEER_ID.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(volunteerService).update(any(), any());
    }

    @Test
    public void shouldReturnDeletedByIdCorrectAnimalShelter() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_VOLUNTEER);
        when(volunteerService.delete(any())).thenReturn(Optional.of(CORRECT_VOLUNTEER));
        mockMvc.perform(
                        delete("/volunteer/" + CORRECT_VOLUNTEER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_VOLUNTEER_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(volunteerService).delete(any());
    }
}