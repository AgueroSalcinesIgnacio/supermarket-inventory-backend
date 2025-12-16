package com.supermarket.auth.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.supermarket.auth.domain.model.User;
import com.supermarket.auth.domain.model.UserRole;
import com.supermarket.auth.infrastructure.adapters.input.dto.AuthResponseDTO;
import com.supermarket.auth.infrastructure.adapters.input.dto.LoginRequestDTO;
import com.supermarket.auth.infrastructure.adapters.input.dto.RegisterRequestDTO;
import com.supermarket.auth.infrastructure.adapters.output.entity.UserEntity;
import com.supermarket.auth.infrastructure.adapters.output.mapper.UserEntityMapperImpl;
import com.supermarket.auth.infrastructure.adapters.output.repository.UserRepository;
import com.supermarket.auth.infrastructure.config.security.JwtService;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserEntityMapperImpl userMapper;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JwtService jwtService;
  @Mock
  private AuthenticationManager authenticationManager;

  @InjectMocks
  private AuthenticationService authenticationService;

  @Test
  void register_ShouldReturnToken_WhenUserIsNotRegistered() {
    // Given
    RegisterRequestDTO request = RegisterRequestDTO.builder().username("jdoe")
        .email("jdoe@example.com").password("password123").build();

    UserEntity savedEntity = new UserEntity();
    User domainUser = User.builder().username("jdoe").email("jdoe@example.com")
        .roles(new HashSet<>(Collections.singletonList(UserRole.USER))).build();

    when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
    when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
    when(userRepository.save(any(UserEntity.class))).thenReturn(savedEntity);
    when(userMapper.toDomain(savedEntity)).thenReturn(domainUser);
    when(jwtService.generateToken(domainUser)).thenReturn("jwt-token");

    // When
    AuthResponseDTO response = authenticationService.register(request);

    // Then
    assertNotNull(response);
    assertEquals("jwt-token", response.getToken());
    assertEquals("jdoe", response.getUsername());
    assertEquals("jdoe@example.com", response.getEmail());

    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void register_ShouldThrowException_WhenUsernameExists() {
    // Given
    RegisterRequestDTO request =
        RegisterRequestDTO.builder().username("existing").email("new@example.com").build();

    when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

    // When & Then
    assertThrows(IllegalArgumentException.class, () -> authenticationService.register(request),
        "Username already exists");

    verify(userRepository, never()).save(any(UserEntity.class));
  }

  @Test
  void register_ShouldThrowException_WhenEmailExists() {
    // Given
    RegisterRequestDTO request =
        RegisterRequestDTO.builder().username("new").email("existing@example.com").build();

    when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

    // When & Then
    assertThrows(IllegalArgumentException.class, () -> authenticationService.register(request),
        "Email already exists");

    verify(userRepository, never()).save(any(UserEntity.class));
  }

  @Test
  void login_ShouldReturnToken_WhenCredentialsAreValid() {
    // Given
    LoginRequestDTO request =
        LoginRequestDTO.builder().username("jdoe").password("password123").build();

    UserEntity userEntity = new UserEntity();
    User domainUser = User.builder().username("jdoe").email("jdoe@example.com")
        .roles(new HashSet<>(Collections.singletonList(UserRole.USER))).build();

    when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(userEntity));
    when(userMapper.toDomain(userEntity)).thenReturn(domainUser);
    when(jwtService.generateToken(domainUser)).thenReturn("jwt-token");

    // When
    AuthResponseDTO response = authenticationService.login(request);

    // Then
    assertNotNull(response);
    assertEquals("jwt-token", response.getToken());
    assertEquals("jdoe", response.getUsername());

    verify(authenticationManager).authenticate(any());
  }

  @Test
  void login_ShouldThrowException_WhenUserNotFound() {
    // Given
    LoginRequestDTO request =
        LoginRequestDTO.builder().username("unknown").password("password123").build();

    when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

    // When & Then
    assertThrows(IllegalArgumentException.class, () -> authenticationService.login(request));
  }
}
