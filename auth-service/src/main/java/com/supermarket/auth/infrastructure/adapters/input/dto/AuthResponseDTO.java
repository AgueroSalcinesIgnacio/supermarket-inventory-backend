package com.supermarket.auth.infrastructure.adapters.input.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for authentication responses containing JWT token.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

  /** The JWT access token. */
  private String token;

  /** The type of the token, defaults to "Bearer". */
  @Builder.Default
  private String type = "Bearer";

  /** The username associated with the token. */
  private String username;

  /** The email associated with the token. */
  private String email;

  /**
   * Constructs a new AuthResponseDTO.
   *
   * @param token the JWT access token
   * @param username the username
   * @param email the email
   */
  public AuthResponseDTO(String token, String username, String email) {
    this.token = token;
    this.username = username;
    this.email = email;
    this.type = "Bearer";
  }
}
