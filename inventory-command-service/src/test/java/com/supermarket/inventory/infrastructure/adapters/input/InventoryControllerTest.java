package com.supermarket.inventory.infrastructure.adapters.input;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermarket.inventory.application.service.InventoryService;
import com.supermarket.inventory.infrastructure.adapters.input.dto.CreateProductRequest;
import com.supermarket.inventory.infrastructure.adapters.input.dto.ProcessOrderRequest;
import com.supermarket.inventory.infrastructure.adapters.input.dto.ReceiveShipmentRequest;
import com.supermarket.inventory.infrastructure.config.security.JwtService;
import com.supermarket.inventory.utils.TestUtils;

@WebMvcTest(InventoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @MockBean
    private JwtService jwtService;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void processOrder_ShouldReturnOk() throws Exception {
        ProcessOrderRequest request = TestUtils.createProcessOrderRequest();

        doNothing().when(inventoryService).processOrder(any(ProcessOrderRequest.class));

        mockMvc.perform(post("/api/inventory/order").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)).secure(false))
                .andExpect(status().isOk());
    }

    @Test
    void receiveShipment_ShouldReturnOk() throws Exception {
        ReceiveShipmentRequest request = TestUtils.createReceiveShipmentRequest();

        doNothing().when(inventoryService).receiveShipment(any(ReceiveShipmentRequest.class));

        mockMvc.perform(post("/api/inventory/shipment").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)).secure(false))
                .andExpect(status().isOk());
    }

    @Test
    void createProduct_ShouldReturnCreated() throws Exception {
        CreateProductRequest request = TestUtils.createProductRequest();

        doNothing().when(inventoryService).createProduct(any(CreateProductRequest.class));

        mockMvc.perform(post("/api/inventory/product").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)).secure(false))
                .andExpect(status().isCreated());
    }
}
