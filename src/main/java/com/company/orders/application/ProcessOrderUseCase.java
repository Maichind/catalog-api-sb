package com.company.orders.application;

import com.company.orders.domain.Order;
import com.company.orders.domain.port.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
public class ProcessOrderUseCase {
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final EmailService emailService;
    private final InventoryService inventoryService;

    public ProcessOrderUseCase(OrderRepository orderRepository,
                               PaymentService paymentService,
                               EmailService emailService,
                               InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.emailService = emailService;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public void execute(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada: " + orderId));

        PaymentResult result = paymentService.charge(order);
        if (!result.approved()) {
            order.markAsFailed(result.failureReason());
            orderRepository.save(order);
            return;
        }

        order.markAsPaid(result.transactionId());
        inventoryService.decreaseStock(order.getProductId(), order.getQuantity());
        orderRepository.save(order);

        notifyCustomerAsync(order); // no bloquea el flujo transaccional principal
    }

    @Async
    protected CompletableFuture<Void> notifyCustomerAsync(Order order) {
        emailService.sendOrderConfirmation(order);
        return CompletableFuture.completedFuture(null);
    }
}
