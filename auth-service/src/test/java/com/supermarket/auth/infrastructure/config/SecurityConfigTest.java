package com.supermarket.auth.infrastructure.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.supermarket.auth.infrastructure.config.security.JwtAuthenticationFilter;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

  @Mock private JwtAuthenticationFilter jwtAuthenticationFilter;
  @Mock private UserDetailsService userDetailsService;

  @InjectMocks private SecurityConfig securityConfig;

  @Test
  void passwordEncoder_ShouldReturnBCryptPasswordEncoder() {
    PasswordEncoder encoder = securityConfig.passwordEncoder();
    assertNotNull(encoder);
    assertTrue(encoder instanceof BCryptPasswordEncoder);
  }

  @Test
  void authenticationProvider_ShouldReturnDaoAuthenticationProvider() {
    AuthenticationProvider provider = securityConfig.authenticationProvider();
    assertNotNull(provider);
    assertTrue(provider instanceof DaoAuthenticationProvider);
  }

  @Test
  void authenticationManager_ShouldReturnAuthenticationManager() throws Exception {
    AuthenticationConfiguration authConfig = mock(AuthenticationConfiguration.class);
    AuthenticationManager authManager = mock(AuthenticationManager.class);
    when(authConfig.getAuthenticationManager()).thenReturn(authManager);

    AuthenticationManager result = securityConfig.authenticationManager(authConfig);

    assertNotNull(result);
    verify(authConfig).getAuthenticationManager();
  }
}
