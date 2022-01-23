package service;

import model.User;

public interface UserService {
    Long save(User user);

    boolean existsByChatId(Long chatId);

    User findByChatId(Long chatId);

    void update(User user);
}
