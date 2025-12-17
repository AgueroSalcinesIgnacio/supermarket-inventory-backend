package com.supermarket.inventory.command.infrastructure.adapters.output.ports;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.supermarket.common.domain.model.OutboxEntity;
import com.supermarket.inventory.command.infrastructure.adapters.output.ports.repository.OutboxRepository;

@ExtendWith(MockitoExtension.class)
class OutboxRepositoryPortImplTest {

    @Mock
    private OutboxRepository outboxRepository;

    @InjectMocks
    private OutboxRepositoryPortImpl outboxRepositoryPort;

    @Test
    void save_ShouldSaveOutboxEntity() {
        // Arrange
        OutboxEntity outbox = OutboxEntity.builder().build();

        // Act
        outboxRepositoryPort.save(outbox);

        // Assert
        verify(outboxRepository).save(outbox);
    }
}
