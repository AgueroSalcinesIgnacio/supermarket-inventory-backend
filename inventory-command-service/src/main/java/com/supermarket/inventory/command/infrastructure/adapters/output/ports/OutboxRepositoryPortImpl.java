package com.supermarket.inventory.command.infrastructure.adapters.output.ports;

import java.util.Objects;
import org.springframework.stereotype.Component;
import com.supermarket.common.domain.model.OutboxEntity;
import com.supermarket.inventory.command.domain.ports.output.OutboxRepositoryPort;
import com.supermarket.inventory.command.infrastructure.adapters.output.ports.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxRepositoryPortImpl implements OutboxRepositoryPort {

    private final OutboxRepository outboxRepository;

    @Override
    public void save(OutboxEntity outboxEntity) {
        outboxRepository.save(Objects.requireNonNull(outboxEntity));
    }
}
