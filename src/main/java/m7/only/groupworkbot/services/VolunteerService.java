package m7.only.groupworkbot.services;

import m7.only.groupworkbot.entity.user.Volunteer;

import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    Volunteer add(Volunteer volunteer);

    List<Volunteer> getAll();

    Optional<Volunteer> getById(Long id);

    Optional<Volunteer> update(Long id, Volunteer volunteer);

    Optional<Volunteer> delete(Long id);
}
