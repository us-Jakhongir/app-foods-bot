package repository.impl;

import model.Cart;
import repository.CartRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static config.DbConfig.connection;

public class CartRepositoryImpl implements CartRepository {

    @Override
    public void save(Cart cart) {
        String INSERT_NEW_CART = "ISERT INTO cart(user_id) VALUES (?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_NEW_CART);
            statement.setLong(1, cart.getUserId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Cart findById(Long id) {
        return null;
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return false;
    }

    @Override
    public Cart findByUserID(Long userId) {
        String SELECT_BY_USER_ID = "SELECT * FROM cart WHERE user_id = " + userId;


        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return new Cart(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id")
                );

        } catch (SQLException e) {
            e.printStackTrace();
        }




        return null;
    }
}
