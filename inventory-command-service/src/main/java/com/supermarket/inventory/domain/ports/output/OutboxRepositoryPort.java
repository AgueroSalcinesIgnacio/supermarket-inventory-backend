package com.supermarket.inventory.domain.ports.output;

import com.supermarket.common.domain.model.OutboxEntity;

/**
 * Interface for the outbox repository port.
 * 
 * @author Ignacio Agüero Salcines
 */
public interface OutboxRepositoryPort {

    /**
     * Saves the given outbox in the repository.
     * 
     * @param outbox
     */
    void save(OutboxEntity outbox);

}
