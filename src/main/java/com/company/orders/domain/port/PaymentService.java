package com.company.orders.domain.port;

import com.company.orders.domain.Order;

public interface PaymentService {
    PaymentResult charge(Order order);
}
