package com.supermarket.inventory.consumer.infrastructure.adapters.output.ports;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.supermarket.common.domain.model.InventoryProjectionEntity;
import com.supermarket.inventory.consumer.domain.ports.output.InventoryProjectionRepositoryPort;
import com.supermarket.inventory.consumer.infrastructure.adapters.output.ports.repository.InventoryProjectionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryProjectionRepositoryPortImpl implements InventoryProjectionRepositoryPort {

    private final InventoryProjectionRepository inventoryRepository;

    @Override
    public Optional<InventoryProjectionEntity> findByProductId(UUID productId) {
        return inventoryRepository.findByProductId(productId);
    }

    @Override
    public InventoryProjectionEntity save(InventoryProjectionEntity inventoryEntity) {
        return inventoryRepository.save(Objects.requireNonNull(inventoryEntity));
    }

}
