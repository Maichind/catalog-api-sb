package com.company.orders.domain.port;

import com.company.orders.domain.Order;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(String orderId);
    void save(Order order);
}
