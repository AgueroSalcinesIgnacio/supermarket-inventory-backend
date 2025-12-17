package com.supermarket.inventory.domain.exception;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException() {
        super("Inventory not found");
    }

    public InventoryNotFoundException(String message) {
        super(message);
    }
}
