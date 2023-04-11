package m7.only.groupworkbot.repository;

import m7.only.groupworkbot.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий сущности User
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Метод ищет пользователя по идентификатору чата
     *
     * @param chatId идентификатор telegram чата
     * @return {@code Nullable Optional<User>}
     */
    Optional<User> findByChatId(Long chatId);

    List<User> findUserByStepParentIsTrue();
}
