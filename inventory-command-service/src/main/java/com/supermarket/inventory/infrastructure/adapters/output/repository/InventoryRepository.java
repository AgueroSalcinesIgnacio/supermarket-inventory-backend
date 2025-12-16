package com.supermarket.inventory.infrastructure.adapters.output.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.supermarket.common.domain.model.InventoryEntity;

/**
 * Spring Data JPA repository for Inventory entities.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, UUID> {

  /**
   * Finds an inventory by productId.
   *
   * @param productId the productId
   * @return Optional containing the inventory if found
   */
  Optional<InventoryEntity> findByProductId(String productId);



}
