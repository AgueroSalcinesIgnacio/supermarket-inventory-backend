package com.supermarket.auth.infrastructure.adapters.output.entity;

import com.supermarket.auth.domain.model.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity for role persistence.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Entity
@Table(name = "ROLES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    /** Unique identifier for the role. */
    @Id
    private Integer id;

    /** The role name (USER, ADMIN). */
    @Enumerated(EnumType.STRING)
    @Column(length = 50, unique = true, nullable = false)
    private UserRole name;
}
