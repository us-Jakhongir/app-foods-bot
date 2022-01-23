package repository;

import model.Category;
import model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public List<Product> findAllByCategoryId(long categoryId) {
        List<Product> products = new ArrayList<>();

        String SELECT_ALL_PRODUCT_BY_CATEGORY = "Select * From product Where category_id = " + categoryId;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCT_BY_CATEGORY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                products.add(new Product(
                resultSet.getLong("id"),
                resultSet.getLong("category_id"),
                resultSet.getString("name"),
                resultSet.getDouble("price"),
                resultSet.getString("image_url")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product findById(Long id) {
        String SELECT_ALL_PRODUCT_BY_CATEGORY = "Select * From product Where id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCT_BY_CATEGORY);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                return new Product(
                        resultSet.getLong("id"),
                        resultSet.getLong("category_id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getString("image_url"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
