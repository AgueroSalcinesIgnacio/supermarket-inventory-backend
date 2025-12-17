package com.supermarket.inventory.command.utils;

import java.math.BigDecimal;

import com.supermarket.inventory.command.infrastructure.adapters.input.dto.CreateProductRequest;
import com.supermarket.inventory.command.infrastructure.adapters.input.dto.ProcessOrderRequest;
import com.supermarket.inventory.command.infrastructure.adapters.input.dto.ReceiveShipmentRequest;

public class TestUtils {

    public static CreateProductRequest createProductRequest() {
        return CreateProductRequest.builder().productCode("product-1").name("Test Product").category("Test Category")
                .price(BigDecimal.TEN).build();
    }

    public static ProcessOrderRequest createProcessOrderRequest() {
        return ProcessOrderRequest.builder().productCode("product-1").quantity(10).build();
    }

    public static ReceiveShipmentRequest createReceiveShipmentRequest() {
        return ReceiveShipmentRequest.builder().productCode("product-1").quantity(10).build();
    }
}
