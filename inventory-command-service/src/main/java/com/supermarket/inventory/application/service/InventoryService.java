package com.supermarket.inventory.application.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermarket.common.domain.events.inventory.InventoryEvent;
import com.supermarket.common.domain.model.InventoryEntity;
import com.supermarket.common.domain.model.OutboxEntity;
import com.supermarket.common.domain.model.ProductEntity;
import com.supermarket.inventory.domain.exception.ProductAlreadyExistsException;
import com.supermarket.inventory.domain.exception.ProductNotFoundException;
import com.supermarket.inventory.infrastructure.adapters.input.dto.CreateProductRequest;
import com.supermarket.inventory.infrastructure.adapters.input.dto.ProcessOrderRequest;
import com.supermarket.inventory.infrastructure.adapters.input.dto.ReceiveShipmentRequest;
import com.supermarket.inventory.infrastructure.adapters.output.repository.InventoryRepository;
import com.supermarket.inventory.infrastructure.adapters.output.repository.OutboxRepository;
import com.supermarket.inventory.infrastructure.adapters.output.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

        private final InventoryRepository inventoryRepository;
        private final OutboxRepository outboxRepository;
        private final ProductRepository productRepository;

        private final ObjectMapper objectMapper;

        @Transactional
        public void processOrder(ProcessOrderRequest request) throws JsonProcessingException {
                log.info("Processing order for product: {}, quantity: {}", request.getProductId(),
                                request.getQuantity());


                // 1. Validate stock availability
                Optional<InventoryEntity> inventory =
                                inventoryRepository.findByProductId(request.getProductId());
                if (inventory.isEmpty()
                                || inventory.get().getStockLocal() < request.getQuantity()) {
                        throw new RuntimeException(
                                        "Not enough stock for product: " + request.getProductId());
                }

                InventoryEntity inventoryEntity = inventory.get();

                // 2. Update stock
                inventoryEntity.setStockLocal(
                                inventoryEntity.getStockLocal() - request.getQuantity());
                inventoryRepository.save(inventoryEntity);


                // 3. Publish event
                InventoryEvent event = InventoryEvent.reduceStock(inventoryEntity);
                OutboxEntity outbox = OutboxEntity.builder().id(event.getEventId())
                                .occurredOn(event.getTimestamp())
                                .aggregateType(event.getAggregateType())
                                .aggregateId(event.getAggregateId()).type(event.getEventType())
                                .payload(objectMapper.writeValueAsString(event))
                                .topic(event.getTopicName()).build();
                outboxRepository.save(outbox);


        }

        @Transactional
        public void receiveShipment(ReceiveShipmentRequest request) throws JsonProcessingException {
                log.info("Receiving shipment for product: {}, quantity: {}", request.getProductId(),
                                request.getQuantity());

                // 1. Search for the product
                InventoryEntity inventory =
                                inventoryRepository.findByProductId(request.getProductId())
                                                .orElseThrow(() -> new ProductNotFoundException());

                // 2. Update stock
                inventory.setStockLocal(inventory.getStockLocal() + request.getQuantity());

                // 3. Save the updated inventory
                inventoryRepository.save(inventory);

                // 4. Publish event
                InventoryEvent event = InventoryEvent.receiveShipment(inventory);
                OutboxEntity outbox = OutboxEntity.builder().id(event.getEventId())
                                .occurredOn(event.getTimestamp())
                                .aggregateType(event.getAggregateType())
                                .aggregateId(event.getAggregateId()).type(event.getEventType())
                                .payload(objectMapper.writeValueAsString(event))
                                .topic(event.getTopicName()).build();
                outboxRepository.save(outbox);
        }

        @Transactional
        public void createProduct(CreateProductRequest request) throws JsonProcessingException {
                log.info("Creating product: {}", request.getProductId());

                // 1. Search for the product
                if (productRepository.existsById(request.getProductId())) {
                        throw new ProductAlreadyExistsException();
                }

                // 2. Create the product
                ProductEntity product = ProductEntity.builder().productId(request.getProductId())
                                .name(request.getName()).category(request.getCategory())
                                .price(request.getPrice()).build();
                ProductEntity savedProduct = productRepository.save(product);

                // 3. Create the inventory
                InventoryEntity inventory = InventoryEntity.builder()
                                .productId(savedProduct.getProductId()).stockLocal(0).build();
                InventoryEntity savedInventory = inventoryRepository.save(inventory);

                // 4. Publish event
                InventoryEvent event = InventoryEvent.createProduct(savedInventory);
                OutboxEntity outbox = OutboxEntity.builder().id(event.getEventId())
                                .occurredOn(event.getTimestamp())
                                .aggregateType(event.getAggregateType())
                                .aggregateId(event.getAggregateId()).type(event.getEventType())
                                .payload(objectMapper.writeValueAsString(event))
                                .topic(event.getTopicName()).build();
                outboxRepository.save(outbox);

        }
}
