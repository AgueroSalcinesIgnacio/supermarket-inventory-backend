package com.supermarket.inventory.command.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermarket.common.domain.model.InventoryEntity;
import com.supermarket.common.domain.model.OutboxEntity;
import com.supermarket.common.domain.model.ProductEntity;
import com.supermarket.inventory.command.domain.exception.InventoryNotFoundException;
import com.supermarket.inventory.command.domain.exception.ProductAlreadyExistsException;
import com.supermarket.inventory.command.domain.exception.ProductNotFoundException;
import com.supermarket.inventory.command.domain.ports.output.InventoryRepositoryPort;
import com.supermarket.inventory.command.domain.ports.output.OutboxRepositoryPort;
import com.supermarket.inventory.command.domain.ports.output.ProductRepositoryPort;
import com.supermarket.inventory.command.infrastructure.adapters.input.dto.CreateProductRequest;
import com.supermarket.inventory.command.infrastructure.adapters.input.dto.ProcessOrderRequest;
import com.supermarket.inventory.command.infrastructure.adapters.input.dto.ReceiveShipmentRequest;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepositoryPort inventoryRepository;

    @Mock
    private OutboxRepositoryPort outboxRepository;

    @Mock
    private ProductRepositoryPort productRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void processOrder_ShouldSucceed_WhenStockIsSufficient() throws JsonProcessingException {
        // Arrange
        String productCode = "prod-1";
        java.util.UUID productId = java.util.UUID.randomUUID();

        int quantity = 5;
        int initialStock = 10;

        ProcessOrderRequest request = new ProcessOrderRequest();
        request.setProductCode(productCode);
        request.setQuantity(quantity);

        ProductEntity product = ProductEntity.builder().id(productId).code(productCode).build();
        InventoryEntity inventory =
                InventoryEntity.builder().productId(productId).stockLocal(initialStock).build();

        when(productRepository.findByCode(productCode)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(inventory));
        when(objectMapper.writeValueAsString(any())).thenReturn("event-payload");

        // Act
        inventoryService.processOrder(request);

        // Assert
        assertThat(inventory.getStockLocal()).isEqualTo(initialStock - quantity);
        verify(inventoryRepository).save(inventory);
        verify(outboxRepository).save(any(OutboxEntity.class));
    }

    @Test
    void processOrder_ShouldThrowException_WhenProductNotFound() {
        // Arrange
        String productCode = "prod-1";
        ProcessOrderRequest request = new ProcessOrderRequest();
        request.setProductCode(productCode);
        request.setQuantity(1);

        when(productRepository.findByCode(productCode)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> inventoryService.processOrder(request))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void processOrder_ShouldThrowException_WhenInventoryNotFound() {
        // Arrange
        String productCode = "prod-1";
        UUID productId = UUID.randomUUID();

        ProcessOrderRequest request = new ProcessOrderRequest();
        request.setProductCode(productCode);
        request.setQuantity(1);

        ProductEntity product = ProductEntity.builder().id(productId).code(productCode).build();

        when(productRepository.findByCode(productCode)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> inventoryService.processOrder(request))
                .isInstanceOf(RuntimeException.class).hasMessageContaining("Not enough stock");
    }


    @Test
    void processOrder_ShouldThrowException_WhenStockInsufficient() {
        // Arrange
        String productCode = "prod-1";
        UUID productId = UUID.randomUUID();

        ProcessOrderRequest request = new ProcessOrderRequest();
        request.setProductCode(productCode);
        request.setQuantity(10);

        ProductEntity product = ProductEntity.builder().id(productId).code(productCode).build();
        InventoryEntity inventory =
                InventoryEntity.builder().productId(productId).stockLocal(5).build();

        when(productRepository.findByCode(productCode)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(inventory));

        // Act & Assert
        assertThatThrownBy(() -> inventoryService.processOrder(request))
                .isInstanceOf(RuntimeException.class).hasMessageContaining("Not enough stock");
    }

    @Test
    void receiveShipment_ShouldSucceed_WhenProductExists() throws JsonProcessingException {
        // Arrange
        String productCode = "prod-1";
        UUID productId = UUID.randomUUID();

        int quantity = 10;
        int initialStock = 5;

        ReceiveShipmentRequest request = new ReceiveShipmentRequest();
        request.setProductCode(productCode);
        request.setQuantity(quantity);

        ProductEntity product = ProductEntity.builder().id(productId).code(productCode).build();
        InventoryEntity inventory =
                InventoryEntity.builder().productId(productId).stockLocal(initialStock).build();

        when(productRepository.findByCode(productCode)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(inventory));
        when(objectMapper.writeValueAsString(any())).thenReturn("event-payload");

        // Act
        inventoryService.receiveShipment(request);

        // Assert
        assertThat(inventory.getStockLocal()).isEqualTo(initialStock + quantity);
        verify(inventoryRepository).save(inventory);
        verify(outboxRepository).save(any(OutboxEntity.class));
    }

    @Test
    void receiveShipment_ShouldThrowException_WhenProductNotFound() {
        // Arrange
        String productCode = "prod-1";
        ReceiveShipmentRequest request = new ReceiveShipmentRequest();
        request.setProductCode(productCode);
        request.setQuantity(1);

        when(productRepository.findByCode(productCode)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> inventoryService.receiveShipment(request))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void receiveShipment_ShouldThrowException_WhenInventoryNotFound() {
        // Arrange
        String productCode = "prod-1";
        UUID productId = UUID.randomUUID();

        int quantity = 10;

        ReceiveShipmentRequest request = new ReceiveShipmentRequest();
        request.setProductCode(productCode);
        request.setQuantity(quantity);

        ProductEntity product = ProductEntity.builder().id(productId).code(productCode).build();

        when(productRepository.findByCode(productCode)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> inventoryService.receiveShipment(request))
                .isInstanceOf(InventoryNotFoundException.class);
    }

    @Test
    void createProduct_ShouldSucceed_WhenProductDoesNotExist() throws JsonProcessingException {
        // Arrange
        String productCode = "prod-1";
        java.util.UUID productId = java.util.UUID.randomUUID();

        CreateProductRequest request = new CreateProductRequest();
        request.setProductCode(productCode);
        request.setName("Test Product");
        request.setCategory("Food");
        request.setPrice(BigDecimal.TEN);

        ProductEntity product = ProductEntity.builder().id(productId).code(productCode).build();
        InventoryEntity inventory =
                InventoryEntity.builder().productId(productId).stockLocal(0).build();

        when(productRepository.existsByCode(productCode)).thenReturn(false);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(inventory);
        when(objectMapper.writeValueAsString(any())).thenReturn("event-payload");

        // Act
        inventoryService.createProduct(request);

        // Assert
        verify(productRepository).save(any(ProductEntity.class));
        verify(inventoryRepository).save(any(InventoryEntity.class));
        verify(outboxRepository).save(any(OutboxEntity.class));
    }

    @Test
    void createProduct_ShouldThrowException_WhenProductAlreadyExists() {
        // Arrange
        String productCode = "prod-1";
        CreateProductRequest request = new CreateProductRequest();
        request.setProductCode(productCode);

        when(productRepository.existsByCode(productCode)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> inventoryService.createProduct(request))
                .isInstanceOf(ProductAlreadyExistsException.class);
    }
}
