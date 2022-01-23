package service;

import model.OrderCart;

import java.util.List;

public interface OrderCartService {

    void save(OrderCart orderCart);
    List<OrderCart> findAll();
    OrderCart findById(Long id);

}
