package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.shelter.AnimalShelter;
import m7.only.groupworkbot.repository.AnimalShelterRepository;
import m7.only.groupworkbot.services.AnimalShelterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный класс для работы с сущностью {@link AnimalShelter}
 */
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

    /**
     * Сохранение нового приюта через API.
     *
     * @param animalShelter сохраняемый приют
     * @return {@code animalShelter}
     */
    @Override
    public AnimalShelter add(AnimalShelter animalShelter) {
        return animalShelterRepository.save(animalShelter);

    }

    /**
     * Получение списка всех приютов для API.
     *
     * @return {@code List<AnimalShelter>}
     */
    @Override
    public List<AnimalShelter> getAll() {
        return animalShelterRepository.findAll();
    }

    /**
     * Получение приюта по id для API.
     *
     * @param id идентификатор приюта
     * @return {@code Optional<AnimalShelter>}, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<AnimalShelter> getById(Long id) {
        return animalShelterRepository.findById(id);
    }

    /**
     * Редактирование приюта по id для API. Проходит проверка на существование приюта.
     *
     * @param id            идентификатор приюта
     * @param animalShelter тело редактируемого приюта
     * @return {@code Optional<animalShelter>} если изменен, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<AnimalShelter> update(Long id, AnimalShelter animalShelter) {
        return animalShelterRepository.findById(id).isPresent()
                ? Optional.of(animalShelterRepository.save(animalShelter))
                : Optional.empty();
    }

    /**
     * Удаление приюта по id для API. Проходит проверка на существование приюта.
     *
     * @param id идентификатор приюта
     * @return {@code Optional<AnimalShelter>} если удален, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<AnimalShelter> delete(Long id) {
        Optional<AnimalShelter> optionalAnimalShelter = animalShelterRepository.findById(id);
        if (animalShelterRepository.findById(id).isPresent()) {
            animalShelterRepository.deleteById(id);
            return optionalAnimalShelter;
        } else {
            return Optional.empty();
        }
    }
}
