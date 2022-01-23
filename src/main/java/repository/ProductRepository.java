package repository;

import model.Category;
import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductRepository {
    Product findByName(String name) throws SQLException;
    boolean existsById(Long id) throws SQLException;


    void saveAll(List<Product> products) throws SQLException;

    List<Product> findAllByCategoryId(long categoryId);
}
