package m7.only.groupworkbot.services;

import m7.only.groupworkbot.entity.shelter.AnimalShelter;

import java.util.List;

public interface AnimalShelterService {
    List<AnimalShelter> findAllShelters();
}
