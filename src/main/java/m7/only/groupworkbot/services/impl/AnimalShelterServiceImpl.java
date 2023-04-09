package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import m7.only.groupworkbot.repository.AnimalShelterRepository;
import m7.only.groupworkbot.services.AnimalShelterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalShelterServiceImpl implements AnimalShelterService {
    private final AnimalShelterRepository animalShelterRepository;

    public AnimalShelterServiceImpl(AnimalShelterRepository animalShelterRepository) {
        this.animalShelterRepository = animalShelterRepository;
    }

    @Override
    public List<AnimalShelter> findAllShelters() {
        return animalShelterRepository.findAll();
    }
}
