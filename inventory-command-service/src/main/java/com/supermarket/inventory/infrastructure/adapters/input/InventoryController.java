package com.supermarket.inventory.infrastructure.adapters.input;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.supermarket.inventory.application.service.InventoryService;
import com.supermarket.inventory.infrastructure.adapters.input.dto.CreateProductRequest;
import com.supermarket.inventory.infrastructure.adapters.input.dto.ProcessOrderRequest;
import com.supermarket.inventory.infrastructure.adapters.input.dto.ReceiveShipmentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/order")
    public ResponseEntity<Void> processOrder(@Valid @RequestBody ProcessOrderRequest request)
            throws JsonProcessingException {
        log.info("Received process order request: {}", request);
        inventoryService.processOrder(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/shipment")
    public ResponseEntity<Void> receiveShipment(@Valid @RequestBody ReceiveShipmentRequest request)
            throws JsonProcessingException {
        log.info("Received receive shipment request: {}", request);
        inventoryService.receiveShipment(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/product")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody CreateProductRequest request)
            throws JsonProcessingException {
        log.info("Received create product request: {}", request);
        inventoryService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
