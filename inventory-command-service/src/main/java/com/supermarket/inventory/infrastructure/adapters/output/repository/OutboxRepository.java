package com.supermarket.inventory.infrastructure.adapters.output.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.supermarket.common.domain.model.OutboxEntity;

/**
 * Spring Data JPA repository for Inventory entities.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Repository
public interface OutboxRepository extends JpaRepository<OutboxEntity, UUID> {

}
