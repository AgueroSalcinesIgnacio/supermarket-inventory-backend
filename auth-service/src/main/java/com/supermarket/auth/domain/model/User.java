package com.supermarket.auth.domain.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private UUID id;

    /** The username of the user. */
    private String username;

    /** The email address of the user. */
    private String email;

    /** The encrypted password of the user. */
    private String password;

    /** The roles assigned to the user. */
    private Set<UserRole> roles;

    /** Flag indicating if the user account is enabled. */
    private boolean enabled;

    /** Timestamp when the user was created. */
    private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
