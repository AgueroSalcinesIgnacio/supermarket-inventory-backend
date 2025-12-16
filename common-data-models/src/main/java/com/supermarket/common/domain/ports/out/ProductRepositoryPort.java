package com.supermarket.common.domain.ports.out;

import java.util.List;
import java.util.Optional;
import com.supermarket.common.domain.model.ProductEntity;

/**
 * Output port interface for product persistence operations.
 *
 * <p>
 * This interface defines the contract for data access operations in the system. It abstracts the
 * persistence layer, allowing the domain layer to remain independent of specific database
 * implementations. Implements the hexagonal architecture pattern.
 *
 * <p>
 * The implementation (ProductPersistenceAdapter) bridges between the domain model and the actual
 * database technology (JPA/Hibernate/PostgreSQL).
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
public interface ProductRepositoryPort {
    /**
     * Retrieves all products from the database.
     *
     * @return list of all products, empty list if none exist
     */
    List<ProductEntity> findAll();

    /**
     * Retrieves a product by its identifier.
     *
     * @param id the product identifier
     * @return Optional containing the product if found
     */
    Optional<ProductEntity> findById(String id);

    /**
     * Saves a product to the database.
     *
     * @param product the product to save
     * @return the saved product with assigned identifier
     */
    ProductEntity save(ProductEntity product);

    /**
     * Deletes a product from the database by identifier.
     *
     * @param id the product identifier to delete
     */
    void deleteById(String id);
}
