package m7.only.groupworkbot.repository;

import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий сущности {@linkplain AnimalShelter AnimalShelter}
 */
public interface AnimalShelterRepository extends JpaRepository<AnimalShelter, Long> {

}
