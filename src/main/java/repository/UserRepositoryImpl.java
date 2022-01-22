package repository;


import enums.BotState;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static config.DbConfig.connection;


public class UserRepositoryImpl implements UserRepository {

    @Override
    public boolean existsByChatId(Long chatId) {
        String SELECT_USER_BY_CHAT_ID = "SELECT * FROM users WHERE chat_id = " + chatId;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_CHAT_ID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void save(User user) {
        String INSERT_NEW_USER = "INSERT INTO users(chat_id, first_name, last_name, username, phone_number, bot_state) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_NEW_USER);
            statement.setLong(1, user.getChatId());
            statement.setString(2, user.getFirstname());
            statement.setString(3, user.getLastname());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getBotState().name());

            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByChatId(Long chatId) {
        String SELECT_USER_BY_CHAT_ID = "SELECT * FROM users WHERE chat_id = " + chatId;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_CHAT_ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                return new User(
                        resultSet.getLong("id"),
                        resultSet.getLong("chat_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("username"),
                        resultSet.getString("phone_number"),
                        BotState.fromString(resultSet.getString("bot_state")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(User user) {
        String UPDATE_USER_DATA = "UPDATE users SET first_name = ?, last_name = ?, username = ?, bot_state = ? WHERE id = " + user.getId();


        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_DATA);
            statement.setString(1, user.getFirstname());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getBotState().name());

            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
