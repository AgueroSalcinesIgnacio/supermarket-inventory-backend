package com.supermarket.auth.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.swagger.v3.oas.models.OpenAPI;

class OpenApiConfigTest {

  private final OpenApiConfig openApiConfig = new OpenApiConfig();

  @Test
  void customOpenAPI_ShouldReturnConfiguredOpenAPI() {
    OpenAPI openAPI = openApiConfig.customOpenAPI();

    assertNotNull(openAPI);
    assertNotNull(openAPI.getInfo());
    assertEquals("Auth Service API", openAPI.getInfo().getTitle());
    assertEquals("v1", openAPI.getInfo().getVersion());
    assertEquals("API for Authentication service", openAPI.getInfo().getDescription());
    assertNotNull(openAPI.getInfo().getContact());
    assertEquals("Supermarket Team", openAPI.getInfo().getContact().getName());
  }
}
