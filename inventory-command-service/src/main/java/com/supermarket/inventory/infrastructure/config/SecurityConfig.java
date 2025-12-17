package com.supermarket.inventory.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.supermarket.inventory.infrastructure.config.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

/**
 * Spring Security configuration for the application.
 *
 * <p>
 * This configuration sets up JWT-based authentication with the following features:
 *
 * <ul>
 * <li>Stateless session management (no server-side sessions)
 * <li>JWT token-based authentication
 * <li>BCrypt password encoding
 * <li>Public endpoints for authentication and health checks
 * <li>Protected endpoints requiring valid JWT tokens
 * </ul>
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthFilter;

  /**
   * Configures the security filter chain.
   *
   * @param http the HttpSecurity to configure
   * @return the configured SecurityFilterChain
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth
        // Public endpoints ("/api/inventory/**" will be secured)
        .requestMatchers("/actuator/**").permitAll()
        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
        // Protected endpoints
        .anyRequest().authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
