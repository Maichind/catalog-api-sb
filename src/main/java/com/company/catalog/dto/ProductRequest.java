package com.company.catalog.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150)
    String name;

    @Size(max = 1000)
    String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    BigDecimal price;

    @NotNull
    @Min(value = 0, message = "El stock no puede ser negativo")
    Integer stock;

    @NotBlank
    @Size(max = 80)
    String category;
}
