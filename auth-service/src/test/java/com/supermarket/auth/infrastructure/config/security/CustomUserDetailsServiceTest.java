package com.supermarket.auth.infrastructure.config.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.supermarket.auth.domain.model.User;
import com.supermarket.auth.domain.model.UserRole;
import com.supermarket.auth.infrastructure.adapters.output.entity.RoleEntity;
import com.supermarket.auth.infrastructure.adapters.output.entity.UserEntity;
import com.supermarket.auth.infrastructure.adapters.output.mapper.UserEntityMapperImpl;
import com.supermarket.auth.infrastructure.adapters.output.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserEntityMapperImpl userMapper;

  @InjectMocks
  private CustomUserDetailsService customUserDetailsService;

  @Test
  void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
    String username = "testuser";
    UserEntity userEntity =
        UserEntity.builder().username(username).password("password")
            .roles(new HashSet<>(
                Collections.singletonList(RoleEntity.builder().name(UserRole.USER).build())))
            .build();

    User user = User.builder().username(username).password("password")
        .roles(new HashSet<>(Collections.singletonList(UserRole.USER))).build();

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
    when(userMapper.toDomain(userEntity)).thenReturn(user);

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

    assertNotNull(userDetails);
    assertEquals(username, userDetails.getUsername());
    assertEquals("password", userDetails.getPassword());
  }

  @Test
  void loadUserByUsername_ShouldThrowException_WhenUserDoesNotExist() {
    String username = "nonexistent";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class,
        () -> customUserDetailsService.loadUserByUsername(username));
  }
}
