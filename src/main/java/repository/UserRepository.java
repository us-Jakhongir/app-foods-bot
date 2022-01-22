package repository;

import model.User;

public interface UserRepository {

    boolean existsByChatId(Long chatId);

    void save(User user);

    User findByChatId(Long chatId);

    void update(User user);
}
