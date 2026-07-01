package com.nyxn.orders.application.port.out;

import com.nyxn.orders.domain.model.Order;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(String id);
}
