package com.supermarket.common.domain.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, name = "product_id")
    private UUID productId;

    @Column(nullable = false, name = "stock_local")
    private Integer stockLocal;

    @Version // JPA versioning for optimistic locking
    @Column(nullable = false)
    private Long version;

    @Column(name = "updated_at")
    private Instant updatedAt;

}
