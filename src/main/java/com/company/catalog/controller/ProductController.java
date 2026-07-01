package com.company.catalog.controller;

import com.company.catalog.dto.ProductRequest;
import com.company.catalog.dto.ProductResponse;
import com.company.catalog.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor // reemplaza el constructor manual con 'service'
public class ProductController {
    private final ProductService service;

    @GetMapping
    @Operation(summary = "Lista productos paginados")
    @ApiResponse(responseCode = "200", description = "Listado exitoso")
    public ResponseEntity<Page<ProductResponse>> findAll(
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un producto por ID")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public ResponseEntity<ProductResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea un producto")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        ProductResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un producto existente")
    public ResponseEntity<ProductResponse> update(@PathVariable String id,
                                                  @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un producto")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
