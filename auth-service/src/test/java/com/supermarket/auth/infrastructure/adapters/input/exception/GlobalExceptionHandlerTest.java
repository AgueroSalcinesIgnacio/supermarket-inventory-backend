package com.supermarket.auth.infrastructure.adapters.input.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

  @Test
  void handleValidationExceptions_ShouldReturnBadRequestWithErrors() {
    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("objectName", "fieldName", "error message");

    when(ex.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

    ResponseEntity<GlobalExceptionHandler.ValidationErrorResponse> response =
        exceptionHandler.handleValidationExceptions(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().status());
    assertEquals("Validation Failed", response.getBody().message());
    assertEquals("error message", response.getBody().errors().get("fieldName"));
  }

  @Test
  void handleValidationExceptions_ShouldReturnBadRequestWithEmptyErrors_WhenNoErrors() {
    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);

    when(ex.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getAllErrors()).thenReturn(Collections.emptyList());

    ResponseEntity<GlobalExceptionHandler.ValidationErrorResponse> response =
        exceptionHandler.handleValidationExceptions(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(0, response.getBody().errors().size());
  }
}
