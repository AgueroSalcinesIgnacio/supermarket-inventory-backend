package com.supermarket.inventory.consumer.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermarket.common.domain.events.inventory.InventoryEvent;
import com.supermarket.common.domain.events.inventory.InventoryEventData;

public class TestUtils {

    public static InventoryEvent createInventoryEvent() {
        InventoryEvent event = InventoryEvent.builder()
                .inventoryData(InventoryEventData.builder().productId(UUID.randomUUID()).quantity(10).build()).build();

        return event;
    }

    public static Map<String, Object> createInventoryEventWrapped(InventoryEvent event) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String eventJsonString = objectMapper.writeValueAsString(event);

        Map<String, Object> debeziumPayload = new HashMap<>();
        debeziumPayload.put("id", "495aa37f-81aa-4e62-b65f-3510bdb49237");
        debeziumPayload.put("occurred_on", 1766071083696000L);
        debeziumPayload.put("aggregate_type", "inventory");
        debeziumPayload.put("aggregate_id", event.getAggregateId());
        debeziumPayload.put("type", event.getEventType());
        debeziumPayload.put("payload", eventJsonString);
        debeziumPayload.put("topic", "inventory-events");

        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "struct");
        schema.put("name", "inventory.public.outbox.Value");
        schema.put("optional", false);

        schema.put("fields", List.of());

        Map<String, Object> fullEnvelop = new HashMap<>();
        fullEnvelop.put("schema", schema);
        fullEnvelop.put("payload", debeziumPayload);

        return fullEnvelop;

    }

}
