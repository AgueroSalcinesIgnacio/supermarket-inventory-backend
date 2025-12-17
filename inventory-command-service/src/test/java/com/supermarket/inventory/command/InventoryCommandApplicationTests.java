package com.supermarket.inventory.command;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.supermarket.inventory.command.infrastructure.adapters.input.InventoryController;

@SpringBootTest
class InventoryCommandApplicationTests {

    @Autowired
    private InventoryController inventoryController;

    @Test
    void contextLoads() {
        assertNotNull(inventoryController);
    }
}
