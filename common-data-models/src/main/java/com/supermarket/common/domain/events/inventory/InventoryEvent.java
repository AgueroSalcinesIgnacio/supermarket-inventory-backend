package com.supermarket.common.domain.events.inventory;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supermarket.common.domain.events.DomainEvent;
import com.supermarket.common.domain.model.InventoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Domain event for inventory-related operations.
 *
 * <p>
 * This event is published to Kafka whenever a stock reduction is processed.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class InventoryEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    public static final String TOPIC_NAME = "inventory-events";
    public static final String INVENTORY_AGGREGATE_TYPE = "inventory";

    /** Product data associated with the event */
    @JsonProperty("inventoryData")
    private InventoryEventData inventoryData;

    @Override
    public String getTopicName() {
        return TOPIC_NAME;
    }

    /**
     * Creates a reduce stock event.
     *
     * @param inventory
     *            the inventory that was reduced
     * @return InventoryEvent with REDUCE_STOCK type
     */
    public static InventoryEvent reduceStock(InventoryEntity inventory) {
        InventoryEvent event = InventoryEvent.builder().inventoryData(InventoryEventData.from(inventory)).build();
        event.setTimestamp(Instant.now());
        event.setEventType(InventoryEventType.REDUCE_STOCK);
        event.setAggregateId(inventory.getProductId().toString());
        event.setAggregateType(INVENTORY_AGGREGATE_TYPE);
        return event;
    }

    /**
     * Creates a receive shipment event.
     *
     * @param inventory
     *            the inventory that was received
     * @return InventoryEvent with RECEIVE_SHIPMENT type
     */
    public static InventoryEvent receiveShipment(InventoryEntity inventory) {
        InventoryEvent event = InventoryEvent.builder().inventoryData(InventoryEventData.from(inventory)).build();
        event.setTimestamp(Instant.now());
        event.setEventType(InventoryEventType.RECEIVE_SHIPMENT);
        event.setAggregateId(inventory.getProductId().toString());
        event.setAggregateType(INVENTORY_AGGREGATE_TYPE);
        return event;
    }

    /**
     * Creates a create product event.
     *
     * @param inventory
     *            the inventory that was created
     * @return InventoryEvent with CREATE_PRODUCT type
     */
    public static InventoryEvent createProduct(InventoryEntity inventory) {
        InventoryEvent event = InventoryEvent.builder().inventoryData(InventoryEventData.from(inventory)).build();
        event.setTimestamp(Instant.now());
        event.setEventType(InventoryEventType.CREATE_PRODUCT);
        event.setAggregateId(inventory.getProductId().toString());
        event.setAggregateType(INVENTORY_AGGREGATE_TYPE);
        return event;
    }

}
