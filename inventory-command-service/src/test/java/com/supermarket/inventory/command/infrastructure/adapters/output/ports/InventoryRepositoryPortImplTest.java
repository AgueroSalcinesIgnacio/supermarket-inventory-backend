package com.supermarket.inventory.command.infrastructure.adapters.output.ports;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import com.supermarket.common.domain.model.InventoryEntity;

@DataJpaTest
@Import(InventoryRepositoryPortImpl.class)
class InventoryRepositoryPortImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InventoryRepositoryPortImpl inventoryRepositoryPort;

    @Test
    void findByProductId_ShouldReturnInventory_WhenFound() {
        // Arrange
        java.util.UUID productId = java.util.UUID.randomUUID();
        InventoryEntity inventory =
                InventoryEntity.builder().productId(productId).stockLocal(10).version(0L).build();

        entityManager.persistAndFlush(inventory);

        // Act
        Optional<InventoryEntity> result = inventoryRepositoryPort.findByProductId(productId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getProductId()).isEqualTo(productId);
        assertThat(result.get().getStockLocal()).isEqualTo(10);
    }

    @Test
    void findByProductId_ShouldReturnEmpty_WhenNotFound() {
        // Act
        Optional<InventoryEntity> result =
                inventoryRepositoryPort.findByProductId(java.util.UUID.randomUUID());

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void save_ShouldSaveInventory() {
        // Arrange
        java.util.UUID productId = java.util.UUID.randomUUID();
        InventoryEntity inventory =
                InventoryEntity.builder().productId(productId).stockLocal(5).version(0L).build();

        // Act
        InventoryEntity savedInventory = inventoryRepositoryPort.save(inventory);

        // Assert
        assertThat(savedInventory).isNotNull();
        assertThat(savedInventory.getId()).isNotNull();

        // Verify in DB
        InventoryEntity found = entityManager.find(InventoryEntity.class, savedInventory.getId());
        assertThat(found).isNotNull();
        assertThat(found.getProductId()).isEqualTo(productId);
        assertThat(found.getStockLocal()).isEqualTo(5);
    }
}
