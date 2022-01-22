package service;

import model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {

    Category save(Category category);

    void saveAll(List<Category> categories) throws SQLException;

    List<Category> findAll();

    Category findById(Long id);
}
