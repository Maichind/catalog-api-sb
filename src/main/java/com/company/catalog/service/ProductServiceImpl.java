package com.company.catalog.service;

import com.company.catalog.dto.ProductRequest;
import com.company.catalog.dto.ProductResponse;
import com.company.catalog.entity.Product;
import com.company.catalog.exception.ProductNotFoundException;
import com.company.catalog.mapper.ProductMapper;
import com.company.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // genera el constructor con los 'final' de abajo — reemplaza el constructor manual
@Slf4j                   // genera 'log' sin necesidad de LoggerFactory.getLogger(...)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(String id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return mapper.toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product saved = repository.save(mapper.toEntity(request));
        log.info("Producto creado con id={}", saved.getId());
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse update(String id, ProductRequest request) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.updateDetails(request.getName(), request.getDescription(), request.getPrice(), request.getCategory());
        return mapper.toResponse(product); // dirty checking, no hace falta save()
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
        log.info("Producto eliminado con id={}", id);
    }
}
