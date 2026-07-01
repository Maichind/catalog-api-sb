package com.nyxn.orders.application.port.in;

import com.nyxn.orders.domain.model.Order;

public interface CreateOrderUseCase {
    Order create(String customerId);
}
