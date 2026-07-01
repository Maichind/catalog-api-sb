package com.company.catalog.mapper;

import com.company.catalog.dto.ProductRequest;
import com.company.catalog.dto.ProductResponse;
import com.company.catalog.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    // Sin estado (sin dependencias inyectadas), no necesita @RequiredArgsConstructor.
    // Si en el futuro incorpora dependencias, se le agrega @RequiredArgsConstructor de Lombok.

    public Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(request.getCategory())
                .build();
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getCreatedAt()
        );
    }
}
