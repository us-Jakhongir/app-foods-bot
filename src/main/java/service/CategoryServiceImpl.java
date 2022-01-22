package service;

import model.Category;
import repository.CategoryRepository;
import repository.CategoryRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    public static CategoryRepository categoryRepository = new CategoryRepositoryImpl();


    @Override
    public Category save(Category category) {
        return null;
    }

    @Override
    public void saveAll(List<Category> categories) throws SQLException {
        categoryRepository.saveAll(categories);

    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return null;
    }
}
