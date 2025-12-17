package com.supermarket.inventory.command.domain.ports.output;

import java.util.Optional;

import com.supermarket.common.domain.model.InventoryEntity;

/**
 * Interface for the inventory repository port.
 *
 * @author Ignacio Agüero Salcines
 */
public interface InventoryRepositoryPort {

    /**
     * Finds an inventory by product id.
     *
     * @param productId
     * @return
     */
    Optional<InventoryEntity> findByProductId(java.util.UUID productId);

    /**
     * Saves the given inventory in the repository.
     *
     * @param inventoryEntity
     * @return
     */
    InventoryEntity save(InventoryEntity inventoryEntity);

}
