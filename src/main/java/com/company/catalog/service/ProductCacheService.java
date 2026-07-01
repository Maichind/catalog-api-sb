package com.company.catalog.service;

import com.company.catalog.dto.ProductResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductCacheService {
    @Cacheable(value = "products", key = "#id")
    public ProductResponse getById(String id) {
        // lógica delegada al service real (findById), esta capa solo cachea
        return null; // placeholder ilustrativo de la firma
    }

    @CacheEvict(value = "products", key = "#id")
    public void evict(String id) {
        // se invoca desde ProductServiceImpl.update()/delete()
    }
}
