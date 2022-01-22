package repository;

import model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryRepository {

    Category findByName(String name) throws SQLException;
    boolean existsById(Long id) throws SQLException;


    void saveAll(List<Category> categories) throws SQLException;

}
