package com.supermarket.common.domain.model;

import java.time.Instant;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OUTBOX")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(nullable = false)
    private Instant occurredOn;

    @Column(nullable = false)
    private String aggregateType;

    @Column(nullable = false)
    private String aggregateId;

    @Column(nullable = false)
    private String type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String payload;

    @Column(nullable = false)
    private String topic;

}
