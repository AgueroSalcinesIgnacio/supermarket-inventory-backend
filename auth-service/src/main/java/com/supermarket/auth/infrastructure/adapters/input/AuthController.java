package com.supermarket.auth.infrastructure.adapters.input;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.auth.application.service.AuthenticationService;
import com.supermarket.auth.infrastructure.adapters.input.dto.AuthResponseDTO;
import com.supermarket.auth.infrastructure.adapters.input.dto.LoginRequestDTO;
import com.supermarket.auth.infrastructure.adapters.input.dto.RegisterRequestDTO;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for authentication endpoints.
 *
 * <p>
 * Provides endpoints for user registration and login.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    /** Service for handling authentication logic. */
    private final AuthenticationService authenticationService;

    /**
     * Registers a new user.
     *
     * @param request
     *            the registration request
     * @return ResponseEntity with JWT token
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            AuthResponseDTO response = authenticationService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Authenticates a user.
     *
     * @param request
     *            the login request
     * @return ResponseEntity with JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            AuthResponseDTO response = authenticationService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
