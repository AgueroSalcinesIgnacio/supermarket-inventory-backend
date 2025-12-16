package com.supermarket.common.domain.events;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base class for all domain events in the housing application.
 *
 * <p>
 * This abstract class defines the common structure for all domain events that are published to
 * Kafka. Each event includes a timestamp and event type information for proper event sourcing and
 * audit trails.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class DomainEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Timestamp when the event occurred */
    @JsonProperty("timestamp")
    private Instant timestamp;

    @JsonProperty("eventId")
    private UUID eventId = UUID.randomUUID();

    /** Type of event */
    @JsonProperty("eventType")
    private String eventType;

    /** Aggregrate ID that triggered the event */
    @JsonProperty("aggregateId")
    private String aggregateId;

    /** Type of aggregate that triggered the event */
    @JsonProperty("aggregateType")
    private String aggregateType;

    /**
     * Returns the Kafka topic name for this event.
     *
     * @return topic name
     */
    public abstract String getTopicName();
}
