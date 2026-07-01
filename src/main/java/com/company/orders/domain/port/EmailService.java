package com.company.orders.domain.port;

import com.company.orders.domain.Order;

public interface EmailService {
    void sendOrderConfirmation(Order order);
}
