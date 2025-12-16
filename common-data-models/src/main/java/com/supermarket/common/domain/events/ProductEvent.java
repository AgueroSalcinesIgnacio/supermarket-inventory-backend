package com.supermarket.common.domain.events;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.supermarket.common.domain.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Domain event for product-related operations.
 *
 * <p>
 * This event is published to Kafka whenever a product is created, updated, or deleted. It contains
 * the product data and operation details for consumption by downstream services.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    public static final String TOPIC_NAME = "product-events";

    /** Product data associated with the event */
    @JsonProperty("productData")
    private ProductEventData productData;

    @Override
    public String getTopicName() {
        return TOPIC_NAME;
    }

    /**
     * Creates a product created event.
     *
     * @param product the product that was created
     * @return ProductEvent with PRODUCT_CREATED type
     */
    public static ProductEvent created(ProductEntity product) {
        ProductEvent event =
                ProductEvent.builder().productData(ProductEventData.from(product)).build();
        event.setTimestamp(Instant.now());
        event.setEventType(ProductEventType.CREATED);
        event.setAggregateId(product.getProductId());
        return event;
    }

    /**
     * Creates a product updated event.
     *
     * @param product the product that was updated
     * @return ProductEvent with PRODUCT_UPDATED type
     */
    public static ProductEvent updated(ProductEntity product) {
        ProductEvent event =
                ProductEvent.builder().productData(ProductEventData.from(product)).build();
        event.setTimestamp(Instant.now());
        event.setEventType(ProductEventType.UPDATED);
        event.setAggregateId(product.getProductId());
        return event;
    }

    /**
     * Creates a product deleted event.
     *
     * @param productId the ID of the product that was deleted
     * @param product the product data before deletion
     * @return ProductEvent with PRODUCT_DELETED type
     */
    public static ProductEvent deleted(String productId, ProductEntity product) {
        ProductEvent event =
                ProductEvent.builder().productData(ProductEventData.from(product)).build();
        event.setTimestamp(Instant.now());
        event.setEventType(ProductEventType.DELETED);
        event.setAggregateId(productId);
        return event;
    }
}
