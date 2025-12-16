package com.supermarket.common.domain.events.product;

import com.supermarket.common.domain.model.ProductEntity;

/**
 * Response containing event tracking information and the associated product data.
 *
 * @param eventId the unique identifier of the event
 * @param status the status of the event (PENDING, COMPLETED)
 * @param product the product data associated with the event (may be null for delete operations)
 */
public record ProductEventResponse(String eventId, String status, ProductEntity product) {
}
