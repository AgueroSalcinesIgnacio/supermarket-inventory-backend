package com.supermarket.inventory.infrastructure.adapters.input.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    @NotBlank
    private String productCode;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotNull
    @Positive
    private BigDecimal price;
}
