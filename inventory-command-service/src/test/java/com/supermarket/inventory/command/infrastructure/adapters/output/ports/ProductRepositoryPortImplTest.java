package com.supermarket.inventory.command.infrastructure.adapters.output.ports;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.supermarket.common.domain.model.ProductEntity;
import com.supermarket.inventory.command.infrastructure.adapters.output.ports.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryPortImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductRepositoryPortImpl productRepositoryPort;

    @Test
    void save_ShouldSaveProduct() {
        // Arrange
        ProductEntity product = ProductEntity.builder().code("prod-1").build();
        when(productRepository.save(product)).thenReturn(product);

        // Act
        ProductEntity result = productRepositoryPort.save(product);

        // Assert
        assertThat(result).isEqualTo(product);
        verify(productRepository).save(product);
    }

    @Test
    void existsById_ShouldReturnTrue_WhenExists() {
        // Arrange
        String productId = "prod-1";
        when(productRepository.existsByCode(productId)).thenReturn(true);

        // Act
        boolean result = productRepositoryPort.existsByCode(productId);

        // Assert
        assertThat(result).isTrue();
        verify(productRepository).existsByCode(productId);
    }

    @Test
    void findByCode_ShouldReturnProduct_WhenExists() {
        // Arrange
        String productCode = "prod-1";
        when(productRepository.findByCode(productCode))
                .thenReturn(Optional.of(ProductEntity.builder().code(productCode).build()));

        // Act
        ProductEntity result = productRepositoryPort.findByCode(productCode).get();

        // Assert
        assertThat(result).isNotNull();
        verify(productRepository).findByCode(productCode);
    }
}
