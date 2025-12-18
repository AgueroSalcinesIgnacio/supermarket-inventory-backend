package com.supermarket.inventory.consumer.infrastructure.adapters.output.ports;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import com.supermarket.common.domain.model.InventoryProjectionEntity;

@DataJpaTest
@Import(InventoryProjectionRepositoryPortImpl.class)
class InventoryRepositoryPortImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InventoryProjectionRepositoryPortImpl inventoryProjectionRepositoryPort;

    @Test
    void findByProductId_ShouldReturnInventoryProjection_WhenFound() {
        // Arrange
        UUID productId = UUID.randomUUID();
        InventoryProjectionEntity inventory = InventoryProjectionEntity.builder().productId(productId)
                .productName("Product").currentStock(10).build();

        entityManager.persistAndFlush(inventory);

        // Act
        Optional<InventoryProjectionEntity> result = inventoryProjectionRepositoryPort
                .findByProductId(inventory.getProductId());

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getCurrentStock()).isEqualTo(10);
    }

    @Test
    void findByProductId_ShouldReturnEmpty_WhenNotFound() {
        // Act
        Optional<InventoryProjectionEntity> result = inventoryProjectionRepositoryPort
                .findByProductId(UUID.randomUUID());

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void save_ShouldSaveInventoryProjection() {
        // Arrange
        UUID productId = UUID.randomUUID();
        InventoryProjectionEntity inventory = InventoryProjectionEntity.builder().productId(productId)
                .productName("Product").currentStock(5).build();

        // Act
        InventoryProjectionEntity savedInventory = inventoryProjectionRepositoryPort.save(inventory);

        // Assert
        assertThat(savedInventory).isNotNull();
        assertThat(savedInventory.getProductId()).isNotNull();

        // Verify in DB
        InventoryProjectionEntity found = entityManager.find(InventoryProjectionEntity.class,
                savedInventory.getProductId());
        assertThat(found).isNotNull();
        assertThat(found.getProductId()).isEqualTo(savedInventory.getProductId());
        assertThat(found.getCurrentStock()).isEqualTo(savedInventory.getCurrentStock());
    }
}
