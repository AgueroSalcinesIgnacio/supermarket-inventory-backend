package com.supermarket.common.domain.events.inventory;

/**
 * Constants for inventory event types.
 *
 * <p>
 * Defines all possible event types for inventory domain operations. Using
 * constants instead of string literals prevents typos and improves
 * maintainability.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
public final class InventoryEventType {
    /** Event type when a stock reduction is processed */
    public static final String REDUCE_STOCK = "REDUCE_STOCK";

    /** Event type when a shipment is received */
    public static final String RECEIVE_SHIPMENT = "RECEIVE_SHIPMENT";

    /** Event type when a product is created */
    public static final String CREATE_PRODUCT = "CREATE_PRODUCT";

    private InventoryEventType() {
        // Private constructor to prevent instantiation
    }
}
