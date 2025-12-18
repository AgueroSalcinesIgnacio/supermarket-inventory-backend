package com.supermarket.inventory.consumer.application.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermarket.common.domain.events.inventory.InventoryEvent;
import com.supermarket.common.domain.events.inventory.InventoryEventType;
import com.supermarket.inventory.consumer.application.service.InventoryProjectionService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InventoryConsumer {

    @Autowired
    private InventoryProjectionService projectionService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "inventory.public.outbox", groupId = "inventory-group")
    public void consume(String message) {
        log.info("Event received from Kafka: {}", message);

        try {
            // 1. Parse the message to a JsonNode
            JsonNode rootNode = objectMapper.readTree(message);

            // 2. Parse the message to an InventoryEvent
            InventoryEvent event = objectMapper.readValue(rootNode.get("payload").get("payload").asText(),
                    InventoryEvent.class);

            // 3. Execute logic based on event type
            switch (event.getEventType()) {
                case InventoryEventType.REDUCE_STOCK :
                    projectionService.updateStock(event.getInventoryData().getProductId(),
                            event.getInventoryData().getQuantity());
                    break;
                case InventoryEventType.CREATE_PRODUCT :
                    projectionService.createProductProjection(event);
                    break;
                default :
                    log.warn("Unknown event type: {}", event.getEventType());
                    break;
            }
        } catch (Exception e) {
            log.error("Error processing event: {}", message, e);
        }

    }

}
