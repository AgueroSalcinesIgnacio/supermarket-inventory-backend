package com.supermarket.inventory.consumer.infrastructure.adapters.output.ports.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarket.common.domain.model.InventoryProjectionEntity;

/**
 * Spring Data JPA repository for Inventory Projection entities.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Repository
public interface InventoryProjectionRepository extends JpaRepository<InventoryProjectionEntity, UUID> {

    /**
     * Finds an inventory projection by productId.
     *
     * @param productId
     *            the productId
     * @return Optional containing the inventory if found
     */
    Optional<InventoryProjectionEntity> findByProductId(UUID productId);

}
