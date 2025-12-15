package com.supermarket.auth.infrastructure.adapters.output.entity;

import java.time.LocalDateTime;
import com.supermarket.auth.domain.model.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

  /** Unique identifier for the user. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** The unique username. */
  @Column(nullable = false, unique = true, length = 50)
  private String username;

  /** The unique email address. */
  @Column(nullable = false, unique = true, length = 100)
  private String email;

  /** The encrypted password. */
  @Column(nullable = false)
  private String password;

  /** The user's first name. */
  @Column(name = "first_name", length = 50)
  private String firstName;

  /** The user's last name. */
  @Column(name = "last_name", length = 50)
  private String lastName;

  /** The user's role (USER or ADMIN). */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private UserRole role;

  /** Flag indicating if the account is enabled. */
  @Column(nullable = false)
  private boolean enabled;

  /** Timestamp when the record was created. */
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  /** Timestamp when the record was last updated. */
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
    if (enabled == false && role == null) {
      enabled = true;
      role = UserRole.USER;
    }
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
