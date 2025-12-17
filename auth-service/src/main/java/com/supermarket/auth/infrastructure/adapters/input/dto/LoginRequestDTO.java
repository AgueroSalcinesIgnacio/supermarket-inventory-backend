package com.supermarket.auth.infrastructure.adapters.input.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for login requests.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    /** The username to log in with. */
    @NotBlank(message = "Username is required")
    private String username;

    /** The password to log in with. */
    @NotBlank(message = "Password is required")
    private String password;
}
