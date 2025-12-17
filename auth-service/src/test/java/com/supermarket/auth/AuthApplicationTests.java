package com.supermarket.auth;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.supermarket.auth.infrastructure.adapters.input.AuthController;

@SpringBootTest
class AuthApplicationTests {

    @Autowired
    private AuthController authController;

    @Test
    void contextLoads() {
        assertNotNull(authController);
    }
}
