package m7.only.groupworkbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import m7.only.groupworkbot.entity.shelter.AnimalType;
import m7.only.groupworkbot.services.AnimalShelterService;
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

@WebMvcTest(AnimalShelterController.class)
public class AnimalShelterControllerTest {

    private static final Long CORRECT_ANIMAL_SHELTER_ID = 17L;
    private static final String CORRECT_ANIMAL_SHELTER_ABOUT = "about";
    private static final String CORRECT_ANIMAL_SHELTER_CONTACTS = "animal shelter contacts";
    private static final String CORRECT_ANIMAL_SHELTER_ADDRESS = "animal shelter address";
    private static final String CORRECT_ANIMAL_SHELTER_OPENING_HOURS = "animal shelter opening hours";
    private static final String CORRECT_ANIMAL_SHELTER_SECURITY_RULES = "animal shelter security rules";
    private static final String CORRECT_ANIMAL_SHELTER_SAFETY_RULES = "animal shelter safety rules";
    private static final String CORRECT_ANIMAL_SHELTER_ADOPTION_RULES = "animal shelter adoption rules";
    private static final AnimalShelter CORRECT_ANIMAL_SHELTER = new AnimalShelter(
            CORRECT_ANIMAL_SHELTER_ID,
            CORRECT_ANIMAL_SHELTER_ABOUT,
            AnimalType.CAT,
            CORRECT_ANIMAL_SHELTER_CONTACTS,
            CORRECT_ANIMAL_SHELTER_ADDRESS,
            CORRECT_ANIMAL_SHELTER_OPENING_HOURS,
            CORRECT_ANIMAL_SHELTER_SECURITY_RULES,
            CORRECT_ANIMAL_SHELTER_SAFETY_RULES,
            CORRECT_ANIMAL_SHELTER_ADOPTION_RULES,
            new HashSet<>(),
            new HashSet<>()
    );
    private static final List<AnimalShelter> CORRECT_ANIMAL_SHELTERS_LIST = List.of(CORRECT_ANIMAL_SHELTER);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnimalShelterService animalShelterService;

    @Test
    public void shouldReturnCreatedAnimalShelter() throws Exception {
        when(animalShelterService.add(any())).thenReturn(CORRECT_ANIMAL_SHELTER);
        String json = objectMapper.writeValueAsString(CORRECT_ANIMAL_SHELTER);
        mockMvc.perform(
                        post("/animalShelter")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(animalShelterService).add(any());
    }

    @Test
    public void shouldReturnAnimalSheltersList() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_ANIMAL_SHELTERS_LIST);
        when(animalShelterService.getAll()).thenReturn(CORRECT_ANIMAL_SHELTERS_LIST);
        mockMvc.perform(
                        get("/animalShelter"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(animalShelterService).getAll();
    }

    @Test
    public void shouldReturnCorrectAnimalShelterById() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_ANIMAL_SHELTER);
        when(animalShelterService.getById(any())).thenReturn(Optional.of(CORRECT_ANIMAL_SHELTER));
        mockMvc.perform(
                        get("/animalShelter/" + CORRECT_ANIMAL_SHELTER_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(animalShelterService).getById(any());
    }

    @Test
    public void shouldReturnEditedByIdCorrectAnimalShelter() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_ANIMAL_SHELTER);
        when(animalShelterService.update(any(), any())).thenReturn(Optional.of(CORRECT_ANIMAL_SHELTER));
        mockMvc.perform(
                        put("/animalShelter/" + CORRECT_ANIMAL_SHELTER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_ANIMAL_SHELTER_ID.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(animalShelterService).update(any(), any());
    }

    @Test
    public void shouldReturnDeletedByIdCorrectAnimalShelter() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_ANIMAL_SHELTER);
        when(animalShelterService.delete(any())).thenReturn(Optional.of(CORRECT_ANIMAL_SHELTER));
        mockMvc.perform(
                        delete("/animalShelter/" + CORRECT_ANIMAL_SHELTER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_ANIMAL_SHELTER_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(animalShelterService).delete(any());
    }
}