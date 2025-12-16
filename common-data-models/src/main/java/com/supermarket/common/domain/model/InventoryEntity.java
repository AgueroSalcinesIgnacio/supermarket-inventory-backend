package com.supermarket.common.domain.model;

import java.time.Instant;
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
@Table(name = "INVENTORY")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private Integer stockLocal;

    @Column(nullable = false)
    private Long version;

    private Instant updatedAt;

}
