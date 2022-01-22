package service;

import model.User;
import repository.UserRepository;
import repository.UserRepositoryImpl;

public class UserServicceImpl implements UserService {
    public static UserRepository userRepository = new UserRepositoryImpl();


    @Override
    public void save(User user) {
        userRepository.save(user);

    }

    @Override
    public boolean existsByChatId(Long chatId) {
        return userRepository.existsByChatId(chatId);
    }

    @Override
    public User findByChatId(Long chatId) {
        return userRepository.findByChatId(chatId);
    }
}
