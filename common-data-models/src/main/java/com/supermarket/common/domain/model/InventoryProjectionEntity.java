package com.supermarket.common.domain.model;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVENTORY_PROJECTION")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryProjectionEntity {

    @Id
    private UUID productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer currentStock;

    private String lastUpdatedByEventId;

}
