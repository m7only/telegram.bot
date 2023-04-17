package m7.only.groupworkbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import m7.only.groupworkbot.entity.report.Report;
import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import m7.only.groupworkbot.entity.user.Dialog;
import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.entity.user.Volunteer;
import m7.only.groupworkbot.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    public static final String CORRECT_FULL_NAME = "Чннгизов Имгабур Абрамбабаевич";
    public static final String CORRECT_PHONE = "4564551";
    public static final Long CORRECT_CHAT_ID = 6147483647L;
    public static final LocalDate CORRECT_TRIAL_START = LocalDate.now();
    public static final Integer CORRECT_TRIAL_PERIOD = 30;
    public static final Dialog CORRECT_DIALOG = null;
    public static final AnimalShelter CORRECT_ANIMAL_SHELTER = new AnimalShelter();
    public static final Volunteer CORRECT_VOLUNTEER = new Volunteer();
    public static final Set<Report> CORRECT_REPORTS = Set.of(new Report());
    private static final Long CORRECT_USER_ID = 17L;
    private static final User CORRECT_USER = new User(
            CORRECT_USER_ID,
            CORRECT_FULL_NAME,
            CORRECT_PHONE,
            CORRECT_CHAT_ID,
            true,
            CORRECT_TRIAL_START,
            CORRECT_TRIAL_PERIOD,
            true,
            true,
            true,
            true,
            true,
            true,
            CORRECT_DIALOG,
            CORRECT_ANIMAL_SHELTER,
            CORRECT_VOLUNTEER,
            CORRECT_REPORTS
    );
    private static final List<User> CORRECT_USER_LIST = List.of(CORRECT_USER);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void shouldReturnCreatedAnimalShelter() throws Exception {
        when(userService.add(any())).thenReturn(CORRECT_USER);
        String json = objectMapper.writeValueAsString(CORRECT_USER);
        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(userService).add(any());
    }

    @Test
    public void shouldReturnAnimalSheltersList() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_USER_LIST);
        when(userService.getAll()).thenReturn(CORRECT_USER_LIST);
        mockMvc.perform(
                        get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(userService).getAll();
    }

    @Test
    public void shouldReturnCorrectAnimalShelterById() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_USER);
        when(userService.getById(any())).thenReturn(Optional.of(CORRECT_USER));
        mockMvc.perform(
                        get("/user/" + CORRECT_USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(userService).getById(any());
    }

    @Test
    public void shouldReturnEditedByIdCorrectAnimalShelter() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_USER);
        when(userService.update(any(), any())).thenReturn(Optional.of(CORRECT_USER));
        mockMvc.perform(
                        put("/user/" + CORRECT_USER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_USER_ID.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(userService).update(any(), any());
    }

    @Test
    public void shouldReturnDeletedByIdCorrectAnimalShelter() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_USER);
        when(userService.delete(any())).thenReturn(Optional.of(CORRECT_USER));
        mockMvc.perform(
                        delete("/user/" + CORRECT_USER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_USER_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(userService).delete(any());
    }
}