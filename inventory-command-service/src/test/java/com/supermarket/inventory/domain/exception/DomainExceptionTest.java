package com.supermarket.inventory.domain.exception;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class DomainExceptionTest {

    @Test
    void productAlreadyExistsException_ShouldHaveDefaultMessage() {
        ProductAlreadyExistsException exception = new ProductAlreadyExistsException();
        assertThat(exception.getMessage()).isEqualTo("Product already exists");
    }

    @Test
    void productAlreadyExistsException_ShouldHaveCustomMessage() {
        String message = "Custom error message";
        ProductAlreadyExistsException exception = new ProductAlreadyExistsException(message);
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void productNotFoundException_ShouldHaveDefaultMessage() {
        ProductNotFoundException exception = new ProductNotFoundException();
        assertThat(exception.getMessage()).isEqualTo("Product not found");
    }

    @Test
    void productNotFoundException_ShouldHaveCustomMessage() {
        String message = "Custom error message";
        ProductNotFoundException exception = new ProductNotFoundException(message);
        assertThat(exception.getMessage()).isEqualTo(message);
    }
}
