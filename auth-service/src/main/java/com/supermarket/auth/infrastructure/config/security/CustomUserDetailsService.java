package com.supermarket.auth.infrastructure.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.supermarket.auth.infrastructure.adapters.output.mapper.UserEntityMapperImpl;
import com.supermarket.auth.infrastructure.adapters.output.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Custom UserDetailsService implementation for Spring Security.
 *
 * <p>
 * Loads user-specific data from the database for authentication.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /** Repository for accessing user data. */
    private final UserRepository userRepository;

    /** Mapper for converting user entities to domain models. */
    private final UserEntityMapperImpl userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(userMapper::toDomain)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
