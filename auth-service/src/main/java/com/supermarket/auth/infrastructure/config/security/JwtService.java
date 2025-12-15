package com.supermarket.auth.infrastructure.config.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service for JWT token operations.
 *
 * <p>
 * Handles JWT token generation, validation, and extraction of claims. Uses HMAC-SHA256 algorithm
 * for signing tokens.
 *
 * @author Ignacio Agüero Salcines
 * @since 1.0
 */
@Service
public class JwtService {

  /** Secret key used for signing JWT tokens. */
  @Value("${jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
  private String secretKey;

  /** Expiration time for JWT tokens in milliseconds. */
  @Value("${jwt.expiration:86400000}") // 24 hours in milliseconds
  private long jwtExpiration;

  /**
   * Extracts username from JWT token.
   *
   * @param token the JWT token
   * @return the username
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a specific claim from the token.
   *
   * @param <T> the type of the claim
   * @param token the JWT token
   * @param claimsResolver function to extract the claim
   * @return the extracted claim
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Generates a JWT token for the given user.
   *
   * @param userDetails the user details
   * @return the generated JWT token
   */
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  /**
   * Generates a JWT token with extra claims.
   *
   * @param extraClaims additional claims to include
   * @param userDetails the user details
   * @return the generated JWT token
   */
  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  /**
   * Builds a JWT token.
   *
   * @param extraClaims additional claims
   * @param userDetails user details
   * @param expiration token expiration time
   * @return the JWT token
   */
  private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails,
      long expiration) {
    return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), Jwts.SIG.HS256).compact();
  }

  /**
   * Validates if the token is valid for the given user.
   *
   * @param token the JWT token
   * @param userDetails the user details
   * @return true if valid, false otherwise
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Checks if the token is expired.
   *
   * @param token the JWT token
   * @return true if expired, false otherwise
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extracts expiration date from token.
   *
   * @param token the JWT token
   * @return the expiration date
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts all claims from the token.
   *
   * @param token the JWT token
   * @return all claims
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Gets the signing key for JWT operations.
   *
   * @return the signing key
   */
  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
