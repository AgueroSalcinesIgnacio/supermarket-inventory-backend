package com.supermarket.inventory.consumer.application.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.supermarket.common.domain.events.inventory.InventoryEvent;
import com.supermarket.common.domain.events.inventory.InventoryEventData;
import com.supermarket.common.domain.model.InventoryProjectionEntity;
import com.supermarket.inventory.consumer.domain.exception.InventoryNotFoundException;
import com.supermarket.inventory.consumer.domain.ports.output.InventoryProjectionRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryProjectionService {

    private final InventoryProjectionRepositoryPort inventoryRepository;

    @Transactional
    public void updateStock(UUID productId, int quantity) {
        log.info("Updating stock for product: {}, new quantity: {}", productId, quantity);
        InventoryProjectionEntity inventory =
                inventoryRepository.findByProductId(productId).orElseThrow(
                        () -> new InventoryNotFoundException("Product not found: " + productId));

        inventory.setCurrentStock(quantity);
        inventoryRepository.save(inventory);

        log.info("Stock updated successfully to: {}", quantity);
    }

    @Transactional
    public void createProductProjection(InventoryEvent event) {
        InventoryEventData data = event.getInventoryData();
        log.info("Creating product projection for product: {}", data.getProductId());

        InventoryProjectionEntity entity = InventoryProjectionEntity.builder()
                .productId(data.getProductId()).currentStock(data.getQuantity())
                .productName("Product-" + data.getProductId())
                .lastUpdatedByEventId(event.getEventId().toString()).build();

        inventoryRepository.save(entity);
        log.info("Product projection created successfully");
    }
}
