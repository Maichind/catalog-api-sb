package com.nyxn.orders.infrastructure.adapter.in.rest;

import com.nyxn.orders.domain.model.Order;

public record OrderResponse(String id, String customerId, String status) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(), order.getCustomerId(), order.getStatus().name());
    }
}
