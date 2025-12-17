package com.supermarket.inventory.domain.ports.output;

import com.supermarket.common.domain.model.ProductEntity;

/**
 * Interface for the product repository port.
 * 
 * @author Ignacio Agüero Salcines
 */
public interface ProductRepositoryPort {

    /**
     * Checks if a product with the given code exists in the repository.
     * 
     * @param code
     * @return
     */
    boolean existsByCode(String code);

    /**
     * Saves the given product in the repository.
     * 
     * @param product
     * @return
     */
    ProductEntity save(ProductEntity product);

    /**
     * Finds a product by its code.
     * 
     * @param code
     * @return
     */
    java.util.Optional<ProductEntity> findByCode(String code);

}
