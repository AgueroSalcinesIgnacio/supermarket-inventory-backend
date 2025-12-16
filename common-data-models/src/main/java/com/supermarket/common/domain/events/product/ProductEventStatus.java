package com.supermarket.common.domain.events.product;

/**
 * Constants for house event statuses.
 *
 * <p>
 * Defines all possible event statuses for house domain events. Using constants
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
public final class ProductEventStatus {
    /** Event status when the event is completed successfully */
    public static final String COMPLETED = "COMPLETED";
    /** Event status when the event is pending processing */
    public static final String PENDING = "PENDING";

    private ProductEventStatus() {
        // Private constructor to prevent instantiation
    }
}
