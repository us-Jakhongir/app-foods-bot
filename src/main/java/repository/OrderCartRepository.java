package repository;

import model.Cart;
import model.OrderCart;

import java.util.List;

public interface OrderCartRepository {
    void save(OrderCart orderCart);
    List<OrderCart> findAll();
    OrderCart findById(Long id);

}
