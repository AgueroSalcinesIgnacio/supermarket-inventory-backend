package com.supermarket.auth.infrastructure.adapters.input.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling application-wide exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ValidationErrorResponse response = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), "Validation Failed", errors);

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Response object for validation errors.
     *
     * @param timestamp
     *            the timestamp of the error
     * @param status
     *            the HTTP status code
     * @param message
     *            the error message
     * @param errors
     *            map of field names to error messages
     */
    public record ValidationErrorResponse(LocalDateTime timestamp, int status, String message,
            Map<String, String> errors) {
    }
}
