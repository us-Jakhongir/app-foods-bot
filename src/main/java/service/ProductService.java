package service;

import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    Product save(Product product);

    void saveAll(List<Product> products) throws SQLException;

    List<Product> findAll();

    Product findById(Long id);

}
