package com.supermarket.auth.infrastructure.adapters.output.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity for user persistence.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Entity
@Table(name = "USERS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

  /** Unique identifier for the user. */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /** The unique username. */
  @Column(nullable = false, unique = true)
  private String username;

  /** The unique email address. */
  @Column(nullable = false, unique = true)
  private String email;

  /** The encrypted password. */
  @Column(name = "password_hash", nullable = false)
  private String password;

  /** Flag indicating if the account is enabled. */
  @Column(name = "is_enabled")
  private boolean enabled;

  /** The user's roles. */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "USERS_ROLES", joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles;

  /** Timestamp when the record was created. */
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    if (!enabled) {
      enabled = true;
    }
  }
}
