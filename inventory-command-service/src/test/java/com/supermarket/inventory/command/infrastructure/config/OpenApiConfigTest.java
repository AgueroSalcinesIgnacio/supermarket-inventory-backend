package com.supermarket.inventory.command.infrastructure.config;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import io.swagger.v3.oas.models.OpenAPI;

class OpenApiConfigTest {

    @Test
    void customOpenAPI_ShouldReturnConfiguredInstance() {
        OpenApiConfig config = new OpenApiConfig();
        OpenAPI openAPI = config.customOpenAPI();

        assertThat(openAPI).isNotNull();
        assertThat(openAPI.getInfo()).isNotNull();
        assertThat(openAPI.getInfo().getTitle()).isEqualTo("Inventory Command Service API");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("v1");
        assertThat(openAPI.getInfo().getDescription())
                .isEqualTo("API for Inventory Command Service");
        assertThat(openAPI.getInfo().getContact()).isNotNull();
        assertThat(openAPI.getInfo().getContact().getName()).isEqualTo("Supermarket Team");
        assertThat(openAPI.getInfo().getContact().getEmail()).isEqualTo("test@example.com");

        // Security checks
        assertThat(openAPI.getComponents().getSecuritySchemes()).containsKey("bearerAuth");
        assertThat(openAPI.getSecurity()).hasSize(1);
    }
}
