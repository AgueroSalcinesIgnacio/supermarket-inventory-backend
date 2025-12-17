package com.supermarket.inventory.command.domain.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException() {
        super("Product already exists");
    }

    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
