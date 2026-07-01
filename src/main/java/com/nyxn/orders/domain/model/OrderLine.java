package com.nyxn.orders.domain.model;

import lombok.Getter;

@Getter
public class OrderLine {
    private final String productId;
    private final int quantity;
    private final Money unitPrice;

    public OrderLine(String productId, int quantity, Money unitPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Money subtotal() {
        return unitPrice.multiply(quantity);
    }
}
