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
  USER,

  /** Administrative user role with full system access. */
  ADMIN
}
