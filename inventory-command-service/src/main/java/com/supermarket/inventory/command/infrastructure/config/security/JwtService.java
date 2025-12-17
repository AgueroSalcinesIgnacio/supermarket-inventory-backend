package com.supermarket.inventory.command.infrastructure.config.security;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

/**
 * Service for JWT token operations (Validation only).
 */
@Service
public class JwtService {

    @Value("${jwt.public-key}")
    private String publicKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getVerifyKey()).build().parseSignedClaims(token).getPayload();
    }

    private PublicKey getVerifyKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(publicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Error loading public key", e);
        }
    }
}
