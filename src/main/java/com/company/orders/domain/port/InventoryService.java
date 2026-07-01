package com.company.orders.domain.port;

public interface InventoryService {
    void decreaseStock(String productId, int quantity);
}
