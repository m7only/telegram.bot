package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.user.Volunteer;
import m7.only.groupworkbot.repository.VolunteerRepository;
import m7.only.groupworkbot.services.VolunteerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Сохранение волонтера
     *
     * @param volunteer волонтер, которого нужно сохранить
     * @return volunteer сохраненный волонтер
     */
    @Override
    public Volunteer add(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    /**
     * Получение списка всех волонтеров для API.
     *
     * @return {@code List<Volunteer>}
     */
    @Override
    public List<Volunteer> getAll() {
        return volunteerRepository.findAll();
    }

    /**
     * Получение волонтера по id для API.
     *
     * @param id идентификатор волонтера
     * @return {@code Optional<Volunteer>}, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<Volunteer> getById(Long id) {
        return volunteerRepository.findById(id);
    }

    /**
     * Редактирование волонтера по id для API. Проходит проверка на существование волонтера.
     *
     * @param id        идентификатор волонтера
     * @param volunteer тело редактируемого волонтера
     * @return {@code Optional<volunteer>} если изменен, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<Volunteer> update(Long id, Volunteer volunteer) {
        return volunteerRepository.findById(id).isPresent()
                ? Optional.of(volunteerRepository.save(volunteer))
                : Optional.empty();
    }

    /**
     * Удаление волонтера по id для API. Проходит проверка на существование волонтера.
     *
     * @param id идентификатор волонтера
     * @return {@code Optional<Volunteer>} если удален, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<Volunteer> delete(Long id) {
        Optional<Volunteer> optionalVolunteer = volunteerRepository.findById(id);
        if (volunteerRepository.findById(id).isPresent()) {
            volunteerRepository.deleteById(id);
            return optionalVolunteer;
        } else {
            return Optional.empty();
        }
    }
}
