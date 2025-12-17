package com.supermarket.common.domain.events.inventory;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supermarket.common.domain.model.InventoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for product event payloads.
 *
 * <p>
 * Contains the product information serialized in Kafka events. This DTO ensures
 * consistent event structure across all product-related events.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryEventData {
    @JsonProperty("productId")
    private UUID productId;

    @JsonProperty("quantity")
    private int quantity;

    /**
     * Converts a Inventory domain model to InventoryEventData.
     *
     * @param inventory
     *            the inventory to convert
     * @return InventoryEventData containing inventory information
     */
    public static InventoryEventData from(InventoryEntity inventory) {
        return InventoryEventData.builder().productId(inventory.getId()).quantity(inventory.getStockLocal()).build();
    }
}
