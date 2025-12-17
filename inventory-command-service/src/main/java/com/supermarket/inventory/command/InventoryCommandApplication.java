package com.supermarket.inventory.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.supermarket.inventory", "com.supermarket.common.domain.model"})
public class InventoryCommandApplication {

  public static void main(String[] args) {
    SpringApplication.run(InventoryCommandApplication.class, args);
  }

}
