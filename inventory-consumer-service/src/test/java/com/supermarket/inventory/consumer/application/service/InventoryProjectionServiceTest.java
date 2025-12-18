package com.supermarket.inventory.consumer.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.supermarket.common.domain.events.inventory.InventoryEvent;
import com.supermarket.common.domain.model.InventoryProjectionEntity;
import com.supermarket.inventory.consumer.domain.exception.InventoryNotFoundException;
import com.supermarket.inventory.consumer.domain.ports.output.InventoryProjectionRepositoryPort;
import com.supermarket.inventory.consumer.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
class InventoryProjectionServiceTest {
    @Mock
    private InventoryProjectionRepositoryPort inventoryRepository;

    @InjectMocks
    private InventoryProjectionService inventoryService;

    @Test
    @DisplayName("updateStock: Should update quantity and save when product exists")
    void updateStock_WhenProductExists_ShouldUpdateAndSave() {
        // Arrange
        UUID productId = UUID.randomUUID();
        int newQuantity = 50;
        InventoryProjectionEntity existingEntity = InventoryProjectionEntity.builder().productId(productId)
                .currentStock(10).build();

        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(existingEntity));

        // Act
        inventoryService.updateStock(productId, newQuantity);

        // Assert
        assertEquals(newQuantity, existingEntity.getCurrentStock());
        verify(inventoryRepository, times(1)).save(existingEntity);
        verify(inventoryRepository, times(1)).findByProductId(productId);
    }

    @Test
    @DisplayName("updateStock: Should throw Exception when product does not exist")
    void updateStock_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        UUID productId = UUID.randomUUID();
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InventoryNotFoundException.class, () -> inventoryService.updateStock(productId, 10));
        verify(inventoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("createProductProjection: Should map event data and save new entity")
    void createProductProjection_ShouldSaveNewEntity() {
        // Arrange
        InventoryEvent event = TestUtils.createInventoryEvent();
        // Usamos un ArgumentCaptor para verificar exactamente qué se intentó guardar
        ArgumentCaptor<InventoryProjectionEntity> entityCaptor = ArgumentCaptor
                .forClass(InventoryProjectionEntity.class);

        // Act
        inventoryService.createProductProjection(event);

        // Assert
        verify(inventoryRepository, times(1)).save(entityCaptor.capture());

        InventoryProjectionEntity savedEntity = entityCaptor.getValue();
        assertNotNull(savedEntity);
        assertEquals(event.getInventoryData().getProductId(), savedEntity.getProductId());
        assertEquals(event.getInventoryData().getQuantity(), savedEntity.getCurrentStock());
        assertEquals(event.getEventId().toString(), savedEntity.getLastUpdatedByEventId());
        assertTrue(savedEntity.getProductName().contains(event.getInventoryData().getProductId().toString()));
    }
}
