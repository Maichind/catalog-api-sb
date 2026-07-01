package com.company.catalog.service;

import com.company.catalog.dto.ProductRequest;
import com.company.catalog.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductResponse> findAll(Pageable pageable);
    ProductResponse findById(String id);
    ProductResponse create(ProductRequest request);
    ProductResponse update(String id, ProductRequest request);
    void delete(String id);
}
