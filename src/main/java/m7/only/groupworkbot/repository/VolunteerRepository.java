package m7.only.groupworkbot.repository;

import m7.only.groupworkbot.entity.user.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий сущности {@linkplain Volunteer Volunteer}
 */
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
