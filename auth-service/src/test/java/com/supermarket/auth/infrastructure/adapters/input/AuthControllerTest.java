package com.supermarket.auth.infrastructure.adapters.input;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermarket.auth.application.service.AuthenticationService;
import com.supermarket.auth.infrastructure.adapters.input.dto.AuthResponseDTO;
import com.supermarket.auth.infrastructure.adapters.input.dto.LoginRequestDTO;
import com.supermarket.auth.infrastructure.adapters.input.dto.RegisterRequestDTO;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Mock private AuthenticationService authenticationService;

  @InjectMocks private AuthController authController;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
  }

  @Test
  void register_ShouldReturnCreated_WhenRequestIsValid() throws Exception {
    // Given
    RegisterRequestDTO request =
        RegisterRequestDTO.builder()
            .username("jdoe")
            .email("jdoe@example.com")
            .password("password123")
            .firstName("John")
            .lastName("Doe")
            .build();

    AuthResponseDTO response = new AuthResponseDTO("dummy-token", "jdoe", "jdoe@example.com");

    when(authenticationService.register(any(RegisterRequestDTO.class))).thenReturn(response);

    // When & Then
    mockMvc
        .perform(
            post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.token").value("dummy-token"))
        .andExpect(jsonPath("$.username").value("jdoe"));
  }

  @Test
  void register_ShouldReturnBadRequest_WhenServiceThrowException() throws Exception {
    // Given
    RegisterRequestDTO request =
        RegisterRequestDTO.builder()
            .username("existing")
            .email("existing@example.com")
            .password("password123")
            .firstName("John")
            .lastName("Doe")
            .build();

    when(authenticationService.register(any(RegisterRequestDTO.class)))
        .thenThrow(new IllegalArgumentException("Username already exists"));

    // When & Then
    mockMvc
        .perform(
            post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void login_ShouldReturnOk_WhenCredentialsAreValid() throws Exception {
    // Given
    LoginRequestDTO request =
        LoginRequestDTO.builder().username("jdoe").password("password123").build();

    AuthResponseDTO response = new AuthResponseDTO("dummy-token", "jdoe", "jdoe@example.com");

    when(authenticationService.login(any(LoginRequestDTO.class))).thenReturn(response);

    // When & Then
    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("dummy-token"));
  }

  @Test
  void login_ShouldReturnUnauthorized_WhenServiceThrowsException() throws Exception {
    // Given
    LoginRequestDTO request =
        LoginRequestDTO.builder().username("unknown").password("wrong").build();

    when(authenticationService.login(any(LoginRequestDTO.class)))
        .thenThrow(new RuntimeException("Bad credentials"));

    // When & Then
    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isUnauthorized());
  }
}
