package com.supermarket.inventory.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.supermarket.inventory.consumer", "com.supermarket.common.domain.model"})
public class InventoryConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryConsumerApplication.class, args);
    }

}
