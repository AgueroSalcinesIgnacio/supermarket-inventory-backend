package com.supermarket.inventory.consumer.domain.ports.output;

import java.util.Optional;
import java.util.UUID;

import com.supermarket.common.domain.model.InventoryProjectionEntity;

/**
 * Interface for the inventory repository port.
 *
 * @author Ignacio Agüero Salcines
 */
public interface InventoryProjectionRepositoryPort {

    /**
     * Finds an inventory projection by product id.
     *
     * @param productId
     * @return
     */
    Optional<InventoryProjectionEntity> findByProductId(UUID productId);

    /**
     * Saves the given inventory projection in the repository.
     *
     * @param inventoryEntity
     * @return
     */
    InventoryProjectionEntity save(InventoryProjectionEntity inventoryEntity);

}
