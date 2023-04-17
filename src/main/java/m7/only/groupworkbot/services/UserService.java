package m7.only.groupworkbot.services;

import m7.only.groupworkbot.entity.user.User;

public interface UserService {
    User findUserByChatIdOrCreateNew(Long chatId);

    User save(User user);
}
