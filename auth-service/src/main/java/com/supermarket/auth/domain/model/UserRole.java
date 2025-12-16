package com.supermarket.auth.domain.model;

/**
 * Domain model representing a user in the system.
 *
 * <p>
 * Implements Spring Security's User Roles.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
public enum UserRole {
  /** Standard user role with basic permissions. */
  USER(1),

  /** Administrative user role with full system access. */
  ADMIN(2);

  private final int id;

  UserRole(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
