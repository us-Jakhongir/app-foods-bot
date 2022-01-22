package repository;

import model.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static config.DbConfig.connection;

public class CategoryRepositoryImpl implements CategoryRepository {


    @Override
    public Category findByName(String name) throws SQLException {

        String SELECT_BY_NAME = "SELECT * FROM category WHERE name = '" + name + "'";
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new Category(
                    resultSet.getLong("id"),
                    resultSet.getString("prefix"),
                    resultSet.getString("name"));
        }

        return null;
    }

    @Override
    public void saveAll(List<Category> categories) throws SQLException {

        for (Category category : categories) {

            if (!existsById(category.getId())) {
                String INSERT_CATEGORY = "INSERT INTO category(prefix, name) values (?, ?)";
                PreparedStatement statement = connection.prepareStatement(INSERT_CATEGORY);
                statement.setString(1, category.getPrefix());
                statement.setString(2, category.getName());

                statement.executeUpdate();

            }



        }

    }

    @Override
    public boolean existsById(Long id) throws SQLException {

        String SELECT_BY_NAME = "SELECT * FROM category WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }
}
