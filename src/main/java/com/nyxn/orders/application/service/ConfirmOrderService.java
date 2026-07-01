package com.nyxn.orders.application.service;

import com.nyxn.orders.application.port.in.ConfirmOrderUseCase;
import com.nyxn.orders.application.port.out.NotificationPort;
import com.nyxn.orders.application.port.out.OrderEventPublisherPort;
import com.nyxn.orders.application.port.out.OrderRepositoryPort;
import com.nyxn.orders.domain.model.Order;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConfirmOrderService implements ConfirmOrderUseCase {
    private final OrderRepositoryPort orderRepository;
    private final OrderEventPublisherPort eventPublisher;
    private final NotificationPort notificationPort;

    @Override
    public void confirm(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada: " + orderId));

        order.confirm();                                // regla de negocio vive en el dominio
        orderRepository.save(order);
        eventPublisher.publishOrderConfirmed(order);     // side-effect delegado a un puerto
        notificationPort.notifyOrderConfirmed(order);    // idem — email de confirmación
    }
}
