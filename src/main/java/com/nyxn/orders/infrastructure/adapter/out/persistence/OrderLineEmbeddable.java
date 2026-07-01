package com.nyxn.orders.infrastructure.adapter.out.persistence;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // exigido por JPA
@AllArgsConstructor
public class OrderLineEmbeddable {
    private String productId;
    private int quantity;
    private BigDecimal unitPrice;
}
