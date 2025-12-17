package com.supermarket.inventory.command.infrastructure.adapters.output.ports.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarket.common.domain.model.ProductEntity;

/**
 * Spring Data JPA repository for Inventory entities.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    boolean existsByCode(String code);

    java.util.Optional<ProductEntity> findByCode(String code);

}
