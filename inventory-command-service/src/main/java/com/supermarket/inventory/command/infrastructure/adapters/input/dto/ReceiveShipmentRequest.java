package com.supermarket.inventory.command.infrastructure.adapters.input.dto;

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
public class ReceiveShipmentRequest {
    @NotNull
    private String productCode;

    @NotNull
    @Positive
    private Integer quantity;
}
