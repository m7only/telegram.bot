package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import m7.only.groupworkbot.repository.AnimalShelterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimalShelterServiceImplTest {
    public static final List<AnimalShelter> CORRECT_ANIMAL_SHELTER_LIST = List.of(new AnimalShelter());

    @Mock
    private AnimalShelterRepository animalShelterRepositoryMock;

    @InjectMocks
    private AnimalShelterServiceImpl out;

    @Test
    void shouldCallRepositoryFindAll() {
        when(animalShelterRepositoryMock.findAll()).thenReturn(CORRECT_ANIMAL_SHELTER_LIST);

        assertEquals(CORRECT_ANIMAL_SHELTER_LIST, out.findAllShelters());
        verify(animalShelterRepositoryMock, times(1)).findAll();
    }
}