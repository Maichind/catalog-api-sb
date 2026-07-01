package com.nyxn.orders.application.port.out;

import com.nyxn.orders.domain.model.Order;

public interface NotificationPort {
    void notifyOrderConfirmed(Order order);
}
