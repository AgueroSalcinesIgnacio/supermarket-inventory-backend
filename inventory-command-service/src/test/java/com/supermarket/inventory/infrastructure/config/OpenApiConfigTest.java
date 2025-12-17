package com.supermarket.inventory.infrastructure.config;

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
        assertThat(openAPI.getInfo().getTitle()).isEqualTo("Inventory Service API");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("v1");
        assertThat(openAPI.getInfo().getDescription()).isEqualTo("API for Inventory service");
        assertThat(openAPI.getInfo().getContact()).isNotNull();
        assertThat(openAPI.getInfo().getContact().getName()).isEqualTo("Supermarket Team");
        assertThat(openAPI.getInfo().getContact().getEmail()).isEqualTo("test@example.com");
    }
}
