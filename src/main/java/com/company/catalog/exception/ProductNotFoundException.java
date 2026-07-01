package com.company.catalog.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String id) {
        super("Producto no encontrado con id: " + id);
    }
}
