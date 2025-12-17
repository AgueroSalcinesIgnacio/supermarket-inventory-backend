package com.supermarket.auth.infrastructure.adapters.output.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarket.auth.domain.model.UserRole;
import com.supermarket.auth.infrastructure.adapters.output.entity.RoleEntity;

/**
 * Spring Data JPA repository for User entities.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    /**
     * Finds a role by name.
     *
     * @param name
     *            the name
     * @return Optional containing the user if found
     */
    Optional<RoleEntity> findByName(UserRole name);
}
