package com.supermarket.inventory.domain.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException() {
        super("Product already exists");
    }

    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
