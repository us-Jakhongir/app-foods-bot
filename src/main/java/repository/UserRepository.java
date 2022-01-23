package repository;

import model.User;

public interface UserRepository {

    boolean existsByChatId(Long chatId);

    Long save(User user);

    User findByChatId(Long chatId);

    void update(User user);
}
