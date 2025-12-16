package com.supermarket.inventory.infrastructure.adapters.input.dto;

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
public class ProcessOrderRequest {
    @NotNull
    private String productId;

    @NotNull
    @Positive
    private Integer quantity;
}
