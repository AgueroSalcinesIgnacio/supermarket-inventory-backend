package com.supermarket.inventory.consumer.infrastructure.adapters.input;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.supermarket.common.domain.events.inventory.InventoryEvent;
import com.supermarket.common.domain.events.inventory.InventoryEventType;
import com.supermarket.inventory.consumer.application.service.InventoryProjectionService;
import com.supermarket.inventory.consumer.utils.TestUtils;

@SpringBootTest(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.auto-offset-reset=earliest"})
@AutoConfigureMockMvc(addFilters = false)
@EmbeddedKafka(partitions = 1, controlledShutdown = true, topics = {"inventory.public.outbox"}, brokerProperties = {
        "log.dir=target/embedded-kafka-logs", "auto.create.topics.enable=true"})
@ActiveProfiles("test")
class InventoryKafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @MockBean
    private InventoryProjectionService projectionService;

    @Test
    void testKafkaListenerFlow() throws JsonProcessingException {
        InventoryEvent event = TestUtils.createInventoryEvent();
        event.setEventType(InventoryEventType.REDUCE_STOCK);

        kafkaTemplate.send("inventory.public.outbox", TestUtils.createInventoryEventWrapped(event));

        Awaitility.await().atMost(5, TimeUnit.SECONDS).pollInterval(500, TimeUnit.MILLISECONDS).untilAsserted(() -> {
            verify(projectionService, atLeastOnce()).updateStock(event.getInventoryData().getProductId(),
                    event.getInventoryData().getQuantity());
        });
    }
}
