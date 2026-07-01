package com.nyxn.orders.application.service;

import com.nyxn.orders.application.port.in.CreateOrderUseCase;
import com.nyxn.orders.application.port.out.OrderRepositoryPort;
import com.nyxn.orders.domain.model.Order;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {
    private final OrderRepositoryPort orderRepository;

    @Override
    public Order create(String customerId) {
        Order order = new Order(UUID.randomUUID().toString(), customerId);
        return orderRepository.save(order);
    }
}
