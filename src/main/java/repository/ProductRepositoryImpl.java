package repository;

import model.Category;
import model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static config.DbConfig.connection;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public Product findByName(String name) throws SQLException {
        return null;
    }

    @Override
    public boolean existsById(Long id) throws SQLException {
        String SELECT_BY_NAME = "SELECT * FROM product WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }

    @Override
    public void saveAll(List<Product> products) throws SQLException {
        for (Product p : products) {

            if (!existsById(p.getId())) {
                String INSERT_PRODUCT = "INSERT INTO product(category_id, name, price, image_url) values (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT);
                statement.setLong(1, p.getCategoryId());
                statement.setString(2, p.getName());
                statement.setDouble(3, p.getPrice());
                statement.setString(4, p.getImageUrl());

                statement.executeUpdate();

            }
        }
    }
}
