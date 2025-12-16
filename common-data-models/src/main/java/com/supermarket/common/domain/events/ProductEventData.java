package com.supermarket.common.domain.events;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.supermarket.common.domain.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for product event payloads.
 *
 * <p>
 * Contains the product information serialized in Kafka events. This DTO ensures consistent event
 * structure across all product-related events.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEventData {
    @JsonProperty("productId")
    private String productId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private String category;

    @JsonProperty("price")
    private BigDecimal price;

    /**
     * Converts a Product domain model to ProductEventData.
     *
     * @param product the product to convert
     * @return ProductEventData containing product information
     */
    public static ProductEventData from(ProductEntity product) {
        return ProductEventData.builder().productId(product.getProductId()).name(product.getName())
                .category(product.getCategory()).price(product.getPrice()).build();
    }
}
