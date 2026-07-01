package com.nyxn.orders.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter                                            // aquí SÍ se justifica: es un modelo de persistencia, no de dominio
@NoArgsConstructor(access = AccessLevel.PROTECTED) // exigido por JPA
public class OrderJpaEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false, length = 20)
    private String status;

    @ElementCollection
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLineEmbeddable> lines = new ArrayList<>();

    @Version
    private Long version; // control de concurrencia también a nivel de persistencia de la orden
}
