package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import m7.only.groupworkbot.repository.EndpointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EndpointServiceImplTest {

    public static final List<Endpoint> CORRECT_ENDPOINTS_LIST = List.of(new Endpoint());
    public static final String CORRECT_TEXT = "text";
    public static final String INCORRECT_TEXT = "";

    public static final Endpoint CORRECT_ENDPOINT = new Endpoint(
            CORRECT_TEXT,
            "title",
            "content",
            new AnimalShelter(),
            new Endpoint());
    @Mock
    private EndpointRepository endpointRepositoryMock;

    @InjectMocks
    private EndpointServiceImpl out;

    @Test
    void shouldCallRepositoryFindAll() {
        when(endpointRepositoryMock.findAll()).thenReturn(CORRECT_ENDPOINTS_LIST);

        assertEquals(CORRECT_ENDPOINTS_LIST, out.findAllEndpoints());
        verify(endpointRepositoryMock, times(1)).findAll();
    }

    @Test
    void shouldReturnEndpointOrNullIfNotExist() {
        when(endpointRepositoryMock.findByEndpointText(CORRECT_TEXT))
                .thenReturn(Optional.of(CORRECT_ENDPOINT));
        when(endpointRepositoryMock.findByEndpointText(INCORRECT_TEXT))
                .thenReturn(Optional.empty());

        assertEquals(CORRECT_ENDPOINT, out.findEndpointByEndpointText(CORRECT_TEXT));
        assertNull(out.findEndpointByEndpointText(INCORRECT_TEXT));
    }
}