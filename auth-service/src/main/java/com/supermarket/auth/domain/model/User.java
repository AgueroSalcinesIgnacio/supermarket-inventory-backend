package com.supermarket.auth.domain.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain model representing a user in the system.
 *
 * <p>
 * Implements Spring Security's UserDetails interface for authentication.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

  /** Unique identifier for the user. */
  private Long id;

  /** The username of the user. */
  private String username;

  /** The email address of the user. */
  private String email;

  /** The encrypted password of the user. */
  private String password;

  /** The first name of the user. */
  private String firstName;

  /** The last name of the user. */
  private String lastName;

  /** The role assigned to the user (USER, ADMIN). */
  private UserRole role;

  /** Flag indicating if the user account is enabled. */
  private boolean enabled;

  /** Timestamp when the user was created. */
  private LocalDateTime createdAt;

  /** Timestamp when the user was last updated. */
  private LocalDateTime updatedAt;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }
}
