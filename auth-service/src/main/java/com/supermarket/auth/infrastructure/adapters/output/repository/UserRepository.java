package com.supermarket.auth.infrastructure.adapters.output.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarket.auth.infrastructure.adapters.output.entity.UserEntity;

/**
 * Spring Data JPA repository for User entities.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * Finds a user by username.
     *
     * @param username
     *            the username
     * @return Optional containing the user if found
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Finds a user by email.
     *
     * @param email
     *            the email
     * @return Optional containing the user if found
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Checks if a user exists with the given username.
     *
     * @param username
     *            the username
     * @return true if exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user exists with the given email.
     *
     * @param email
     *            the email
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);
}
