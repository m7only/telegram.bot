package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.repository.UserRepository;
import m7.only.groupworkbot.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Сервисный слой для работы с сущностью {@link User}
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Метод для поиска пользователя по идентификатору телеграм чата
     *
     * @param chatId идентификатор чата
     * @return сущность {@code User} или {@code null} если не найден
     */
    @Override
    public User findUserByChatIdOrCreateNew(Long chatId) {
        return userRepository.findByChatId(chatId).orElseGet(() -> userRepository.save(new User(chatId)));
    }

    /**
     * Сохранение пользователя
     *
     * @param user пользователь, которого нужно сохранить
     * @return User сохраненный пользователь
     */
    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    /**
     * Получение списка всех пользователей для API.
     *
     * @return {@code List<User>}
     */
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Получение пользователя по id для API.
     *
     * @param id идентификатор пользователя
     * @return {@code Optional<User>}, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Редактирование пользователя по id для API. Проходит проверка на существование пользователя.
     *
     * @param id   идентификатор пользователя
     * @param user тело редактируемого пользователя
     * @return {@code Optional<User>} если изменен, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<User> update(Long id, User user) {
        return userRepository.findById(id).isPresent()
                ? Optional.of(userRepository.save(user))
                : Optional.empty();
    }

    /**
     * Удаление пользователя по id для API. Проходит проверка на существование пользователя.
     *
     * @param id идентификатор пользователя
     * @return {@code Optional<User>} если удален, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<User> delete(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return optionalUser;
        } else {
            return Optional.empty();
        }
    }
}
