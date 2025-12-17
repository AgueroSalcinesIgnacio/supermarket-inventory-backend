package com.supermarket.inventory.command.infrastructure.config;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermarket.inventory.command.application.service.InventoryService;
import com.supermarket.inventory.command.infrastructure.config.security.JwtService;
import com.supermarket.inventory.command.utils.TestUtils;

@SpringBootTest
@AutoConfigureMockMvc
class InventorySecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InventoryService inventoryService;

    @Test
    @DisplayName("Should return 200 OK when the token is valid")
    void shouldReturnOkWithValidToken() throws Exception {
        when(jwtService.extractUsername(anyString())).thenReturn("test");

        mockMvc.perform(post("/api/inventory/order").header("Authorization", "Bearer validToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestUtils.createProcessOrderRequest())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 403 Forbidden when there is no token")
    void shouldReturnForbiddenWithoutToken() throws Exception {
        mockMvc.perform(post("/api/inventory/order").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestUtils.createProcessOrderRequest())))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should return 403 Forbidden when the token is manipulated")
    void shouldReturnForbiddenWithInvalidToken() throws Exception {

        when(jwtService.extractUsername(anyString()))
                .thenThrow(new RuntimeException("Invalid token"));

        mockMvc.perform(post("/api/inventory/order").header("Authorization", "Bearer invalidToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestUtils.createProcessOrderRequest())))
                .andExpect(status().isForbidden());
    }
}
