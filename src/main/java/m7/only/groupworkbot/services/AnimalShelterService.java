package m7.only.groupworkbot.services;

import m7.only.groupworkbot.entity.shelter.AnimalShelter;

import java.util.List;
import java.util.Optional;

public interface AnimalShelterService {
    List<AnimalShelter> findAllShelters();

    AnimalShelter add(AnimalShelter animalShelter);

    List<AnimalShelter> getAll();

    Optional<AnimalShelter> getById(Long id);

    Optional<AnimalShelter> update(Long id, AnimalShelter animalShelter);

    Optional<AnimalShelter> delete(Long id);
}
