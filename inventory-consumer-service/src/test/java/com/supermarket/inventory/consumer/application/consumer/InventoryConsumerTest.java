package com.supermarket.inventory.consumer.application.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermarket.common.domain.events.inventory.InventoryEvent;
import com.supermarket.common.domain.events.inventory.InventoryEventType;
import com.supermarket.inventory.consumer.application.service.InventoryProjectionService;
import com.supermarket.inventory.consumer.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
class InventoryConsumerTest {

    @Mock
    private InventoryProjectionService projectionService;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private InventoryConsumer inventoryConsumer;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(inventoryConsumer, "objectMapper", objectMapper);
    }

    @Test
    @DisplayName("Should call updateStock when the event is STOCK_REDUCED_EVENT")
    void consume_ShouldCallUpdateStock_WhenStockReducedEvent() throws Exception {
        // Arrange
        InventoryEvent event = TestUtils.createInventoryEvent();
        event.setEventType(InventoryEventType.REDUCE_STOCK);

        // Act
        inventoryConsumer.consume(objectMapper.writeValueAsString(TestUtils.createInventoryEventWrapped(event)));

        // Assert
        verify(projectionService, times(1)).updateStock(event.getInventoryData().getProductId(),
                event.getInventoryData().getQuantity());
        verify(projectionService, never()).createProductProjection(any());
    }

    @Test
    @DisplayName("Should call createProductProjection when the event is PRODUCT_CREATED_EVENT")
    void consume_ShouldCallCreateProjection_WhenProductCreatedEvent() throws Exception {
        // Arrange
        InventoryEvent event = TestUtils.createInventoryEvent();
        event.setEventType(InventoryEventType.CREATE_PRODUCT);

        // Act
        inventoryConsumer.consume(objectMapper.writeValueAsString(TestUtils.createInventoryEventWrapped(event)));

        // Assert
        verify(projectionService, times(1)).createProductProjection(event);
        verify(projectionService, never()).updateStock(any(UUID.class), any(Integer.class));
    }

    @Test
    @DisplayName("Should do nothing when the event type is unknown")
    void consume_ShouldDoNothing_WhenUnknownEvent() throws Exception {
        // Arrange
        InventoryEvent event = TestUtils.createInventoryEvent();

        // Act
        inventoryConsumer.consume(objectMapper.writeValueAsString(event));

        // Assert
        verifyNoInteractions(projectionService);
    }

    @Test
    @DisplayName("Should do nothing when the event type is empty")
    void consume_ShouldDoNothing_WhenEventTypeIsEmpty() throws Exception {
        // Arrange
        InventoryEvent event = TestUtils.createInventoryEvent();
        event.setEventType("");

        // Act
        inventoryConsumer.consume(objectMapper.writeValueAsString(event));

        // Assert
        verifyNoInteractions(projectionService);
    }
}
