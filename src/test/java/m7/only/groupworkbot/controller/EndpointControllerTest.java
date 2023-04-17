package m7.only.groupworkbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import m7.only.groupworkbot.services.EndpointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EndpointController.class)
public class EndpointControllerTest {
    private static final Long CORRECT_ENDPOINT_ID = 17L;
    private static final Endpoint CORRECT_ENDPOINT = new Endpoint(
            CORRECT_ENDPOINT_ID,
            "/endpoint",
            "endpoint title",
            "endpoint content",
            new AnimalShelter(),
            null,
            new HashSet<>()
    );
    private static final List<Endpoint> CORRECT_ENDPOINTS_LIST = List.of(CORRECT_ENDPOINT);
    private static final Endpoint INCORRECT_ENDPOINT = new Endpoint(
            CORRECT_ENDPOINT_ID,
            "incorrect_endpoint_text",
            "endpoint title",
            "endpoint content",
            new AnimalShelter(),
            null,
            new HashSet<>()
    );
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EndpointService endpointService;

    @Test
    public void shouldReturnCreatedEndpoint() throws Exception {
        when(endpointService.add(any())).thenReturn(Optional.of(CORRECT_ENDPOINT));
        String json = objectMapper.writeValueAsString(CORRECT_ENDPOINT);
        mockMvc.perform(
                        post("/endpoint")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(endpointService).add(any());
    }

    @Test
    public void shouldReturnBadRequest() throws Exception {
        String json = objectMapper.writeValueAsString(INCORRECT_ENDPOINT);
        mockMvc.perform(
                        post("/endpoint")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isBadRequest());
        verify(endpointService).add(any());
    }

    @Test
    public void shouldReturnEndpointsList() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_ENDPOINTS_LIST);
        when(endpointService.getAll()).thenReturn(CORRECT_ENDPOINTS_LIST);
        mockMvc.perform(
                        get("/endpoint"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(endpointService).getAll();
    }

    @Test
    public void shouldReturnCorrectEndpointById() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_ENDPOINT);
        when(endpointService.getById(any())).thenReturn(Optional.of(CORRECT_ENDPOINT));
        mockMvc.perform(
                        get("/endpoint/" + CORRECT_ENDPOINT_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(endpointService).getById(any());
    }

    @Test
    public void shouldReturnEditedByIdCorrectEndpoint() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_ENDPOINT);
        when(endpointService.update(any(), any())).thenReturn(Optional.of(CORRECT_ENDPOINT));
        mockMvc.perform(
                        put("/endpoint/" + CORRECT_ENDPOINT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_ENDPOINT_ID.toString())
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(endpointService).update(any(), any());
    }

    @Test
    public void shouldReturnDeletedByIdCorrectEndpoint() throws Exception {
        String json = objectMapper.writeValueAsString(CORRECT_ENDPOINT);
        when(endpointService.delete(any())).thenReturn(Optional.of(CORRECT_ENDPOINT));
        mockMvc.perform(
                        delete("/endpoint/" + CORRECT_ENDPOINT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CORRECT_ENDPOINT_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(endpointService).delete(any());
    }
}