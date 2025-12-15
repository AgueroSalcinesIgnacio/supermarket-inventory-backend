package com.supermarket.auth.application.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.supermarket.auth.domain.model.User;
import com.supermarket.auth.domain.model.UserRole;
import com.supermarket.auth.infrastructure.adapters.input.dto.AuthResponseDTO;
import com.supermarket.auth.infrastructure.adapters.input.dto.LoginRequestDTO;
import com.supermarket.auth.infrastructure.adapters.input.dto.RegisterRequestDTO;
import com.supermarket.auth.infrastructure.adapters.output.entity.UserEntity;
import com.supermarket.auth.infrastructure.adapters.output.mapper.UserEntityMapperImpl;
import com.supermarket.auth.infrastructure.adapters.output.repository.UserRepository;
import com.supermarket.auth.infrastructure.config.security.JwtService;
import lombok.RequiredArgsConstructor;

/**
 * Service for handling authentication operations.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

  /** Repository for accessing user data. */
  private final UserRepository userRepository;

  /** Mapper for converting between entities and domain models. */
  private final UserEntityMapperImpl userMapper;

  /** Service for encoding and matching passwords. */
  private final PasswordEncoder passwordEncoder;

  /** Service for handling JWT operations. */
  private final JwtService jwtService;

  /** Manager for authentication processing. */
  private final AuthenticationManager authenticationManager;

  /**
   * Registers a new user.
   *
   * @param request the registration request
   * @return authentication response with JWT token
   * @throws IllegalArgumentException if username or email already exists
   */
  public AuthResponseDTO register(RegisterRequestDTO request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new IllegalArgumentException("Username already exists");
    }
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("Email already exists");
    }

    UserEntity user = UserEntity.builder().username(request.getUsername()).email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword())).firstName(request.getFirstName())
        .lastName(request.getLastName()).role(UserRole.USER).enabled(true).build();

    UserEntity savedUser = userRepository.save(user);
    User domainUser = userMapper.toDomain(savedUser);
    String jwtToken = jwtService.generateToken(domainUser);

    return new AuthResponseDTO(jwtToken, domainUser.getUsername(), domainUser.getEmail());
  }

  /**
   * Authenticates a user.
   *
   * @param request the login request
   * @return authentication response with JWT token
   */
  public AuthResponseDTO login(LoginRequestDTO request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    User user = userRepository.findByUsername(request.getUsername()).map(userMapper::toDomain)
        .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

    String jwtToken = jwtService.generateToken(user);

    return new AuthResponseDTO(jwtToken, user.getUsername(), user.getEmail());
  }
}
