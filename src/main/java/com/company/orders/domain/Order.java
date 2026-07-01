package com.company.orders.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // exigido por JPA
public class Order {

    @Id
    private String id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column
    private String paymentTransactionId;

    @Column(length = 500)
    private String failureReason;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Version
    private Long version; // protege contra doble procesamiento concurrente de la misma orden

    public Order(String id, String productId, Integer quantity, BigDecimal total) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.status = OrderStatus.PENDING;
        this.createdAt = Instant.now();
    }

    // Transiciones de estado como métodos de negocio explícitos (no setters genéricos)
    public void markAsPaid(String transactionId) {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Solo una orden PENDING puede marcarse como pagada. Estado actual: " + status);
        }
        this.status = OrderStatus.PAID;
        this.paymentTransactionId = transactionId;
    }

    public void markAsFailed(String reason) {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Solo una orden PENDING puede marcarse como fallida. Estado actual: " + status);
        }
        this.status = OrderStatus.PAYMENT_FAILED;
        this.failureReason = reason;
    }
}
