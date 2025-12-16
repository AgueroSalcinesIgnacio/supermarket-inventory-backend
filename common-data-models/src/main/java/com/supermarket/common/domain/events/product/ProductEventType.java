package com.supermarket.common.domain.events.product;

/**
 * Constants for house event types.
 *
 * <p>
 * Defines all possible event types for house domain operations. Using constants instead of string
 * literals prevents typos and improves maintainability.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
public final class ProductEventType {
    /** Event type when a new product is created */
    public static final String CREATED = "PRODUCT_CREATED";

    /** Event type when an existing product is updated */
    public static final String UPDATED = "PRODUCT_UPDATED";

    /** Event type when a product is deleted */
    public static final String DELETED = "PRODUCT_DELETED";

    private ProductEventType() {
        // Private constructor to prevent instantiation
    }
}
