package service;

import model.User;

public interface UserService {
    void save(User user);

    boolean existsByChatId(Long chatId);

    User findByChatId(Long chatId);

}
