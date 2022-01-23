package repository;

import model.Cart;

import java.util.List;

public interface CartRepository {
    void save(Cart cart);
    List<Cart> findAll();
    Cart findById(Long id);

    Cart findByUserID(Long userId);

    boolean existsByUserId(Long userId);
}
