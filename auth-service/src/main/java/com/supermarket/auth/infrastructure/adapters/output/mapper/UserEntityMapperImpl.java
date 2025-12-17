package com.supermarket.auth.infrastructure.adapters.output.mapper;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.supermarket.auth.domain.model.User;
import com.supermarket.auth.infrastructure.adapters.output.entity.RoleEntity;
import com.supermarket.auth.infrastructure.adapters.output.entity.UserEntity;

/**
 * Manual mapper for User domain model and UserEntity.
 *
 * <p>
 * This is a manual implementation instead of MapStruct to avoid issues with
 * UserDetails interface methods being treated as properties.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Component
public class UserEntityMapperImpl {

    /** Default constructor. */
    public UserEntityMapperImpl() {
    }

    /**
     * Converts UserEntity to User domain model.
     *
     * @param entity
     *            the user entity
     * @return the user domain model
     */
    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder().id(entity.getId()).username(entity.getUsername()).email(entity.getEmail())
                .password(entity.getPassword())
                .roles(entity.getRoles() != null
                        ? entity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet())
                        : Collections.emptySet())
                .enabled(entity.isEnabled()).createdAt(entity.getCreatedAt()).build();
    }

    /**
     * Converts User domain model to UserEntity.
     *
     * @param user
     *            the user domain model
     * @return the user entity
     */
    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        return UserEntity
                .builder().id(user.getId()).username(user.getUsername()).email(
                        user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles() != null
                        ? user.getRoles().stream().map(role -> RoleEntity.builder().id(role.getId()).name(role).build())
                                .collect(Collectors.toSet())
                        : Collections.emptySet())
                .enabled(user.isEnabled()).createdAt(user.getCreatedAt()).build();
    }
}
