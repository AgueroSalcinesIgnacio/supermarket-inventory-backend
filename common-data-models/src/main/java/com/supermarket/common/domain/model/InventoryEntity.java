package com.supermarket.common.domain.model;

import java.time.Instant;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVENTORY")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private Integer stockLocal;

    @Version // JPA versioning for optimistic locking
    @Column(nullable = false)
    private Long version;

    private Instant updatedAt;

}
