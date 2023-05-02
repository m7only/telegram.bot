package m7.only.groupworkbot.services;

import m7.only.groupworkbot.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUserByChatIdOrCreateNew(Long chatId);

    User add(User user);

    List<User> getAll();

    Optional<User> getById(Long id);

    Optional<User> update(Long id, User user);

    Optional<User> delete(Long id);
}
