package com.supermarket.inventory.command.infrastructure.adapters.output.ports;

import java.util.Objects;
import org.springframework.stereotype.Component;
import com.supermarket.common.domain.model.ProductEntity;
import com.supermarket.inventory.command.domain.ports.output.ProductRepositoryPort;
import com.supermarket.inventory.command.infrastructure.adapters.output.ports.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductRepositoryPortImpl implements ProductRepositoryPort {

    private final ProductRepository productRepository;

    @Override
    public ProductEntity save(ProductEntity product) {
        return productRepository.save(Objects.requireNonNull(product));
    }

    @Override
    public boolean existsByCode(String code) {
        return productRepository.existsByCode(code);
    }

    @Override
    public java.util.Optional<ProductEntity> findByCode(String code) {
        return productRepository.findByCode(code);
    }
}
