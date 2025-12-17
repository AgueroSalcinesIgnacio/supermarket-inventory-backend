package com.supermarket.inventory.infrastructure.adapters.output.ports;

import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.supermarket.common.domain.model.InventoryEntity;
import com.supermarket.inventory.domain.ports.output.InventoryRepositoryPort;
import com.supermarket.inventory.infrastructure.adapters.output.ports.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryRepositoryPortImpl implements InventoryRepositoryPort {

    private final InventoryRepository inventoryRepository;

    @Override
    public Optional<InventoryEntity> findByProductId(java.util.UUID productId) {
        return inventoryRepository.findByProductId(productId);
    }

    @Override
    public InventoryEntity save(InventoryEntity inventoryEntity) {
        return inventoryRepository.save(Objects.requireNonNull(inventoryEntity));
    }

}
