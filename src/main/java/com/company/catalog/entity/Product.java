package com.company.catalog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_products_category", columnList = "category")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // exigido por JPA, sin exponerlo fuera del paquete
@ToString(of = {"id", "name", "category", "stock"}) // evita exponer description/price en logs
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, length = 80)
    private String category;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Version
    private Long version; // optimistic locking (ver Sección 3)

    @Builder
    public Product(String name, String description, BigDecimal price, Integer stock, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    // Métodos de negocio: mutación intencional, no setters genéricos
    public void updateDetails(String name, String description, BigDecimal price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public void decreaseStock(int quantity) {
        if (quantity > this.stock) {
            throw new IllegalStateException("Stock insuficiente para producto " + id);
        }
        this.stock -= quantity;
    }
}
