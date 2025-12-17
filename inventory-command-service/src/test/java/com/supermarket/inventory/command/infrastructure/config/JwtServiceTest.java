package com.supermarket.inventory.command.infrastructure.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.supermarket.inventory.command.infrastructure.config.security.JwtService;

import io.jsonwebtoken.Jwts;

public class JwtServiceTest {

    private JwtService jwtService;
    private KeyPair keyPair;
    private String publicKeyStr;

    @BeforeEach
    void setUp() throws Exception {
        jwtService = new JwtService();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
        publicKeyStr = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

        ReflectionTestUtils.setField(jwtService, "publicKey", publicKeyStr);
    }

    @Test
    @DisplayName("Should extract username correctly from a valid token")
    void extractUsername_ShouldReturnSubject_WhenTokenIsValid() {
        // Arrange
        String username = "user@example.com";
        String token = Jwts.builder().subject(username).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60)) // 1 min exp
                .signWith(keyPair.getPrivate(), Jwts.SIG.RS256).compact();

        // Act
        String result = jwtService.extractUsername(token);

        // Assert
        assertThat(result).isEqualTo(username);
    }

    @Test
    @DisplayName("Should throw exception when the token is signed with a different key")
    void extractAllClaims_ShouldThrowException_WhenSignatureIsInvalid() throws Exception {
        // Arrange
        KeyPair otherKeyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        String tokenConOtraFirma = Jwts.builder().subject("hacker").signWith(otherKeyPair.getPrivate(), Jwts.SIG.RS256)
                .compact();

        // Act & Assert
        assertThatThrownBy(() -> jwtService.extractUsername(tokenConOtraFirma)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Should throw exception when the public key is invalid")
    void getVerifyKey_ShouldThrowException_WhenKeyIsInvalid() {
        // Arrange
        ReflectionTestUtils.setField(jwtService, "publicKey", "not-a-base64-key");

        // Act & Assert
        assertThatThrownBy(() -> jwtService.extractUsername("any.token.here")).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error loading public key");
    }
}
