package com.company.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {
    String id;
    String name;
    String description;
    BigDecimal price;
    Integer stock;
    String category;
    Instant createdAt;
}
