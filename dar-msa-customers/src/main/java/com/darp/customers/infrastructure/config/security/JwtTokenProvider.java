package com.darp.customers.infrastructure.config.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
  private final SecretKey jwtKey;

  @Value("${jwt.expiration}")
  private long jwtExpiration;

  public String generateToken(String customerId) {
    var now = new Date();
    var expiryDate = new Date(now.getTime() + jwtExpiration);

    return Jwts.builder()
        .subject(customerId)
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(jwtKey)
        .compact();
  }

  public String getCustomerIdFromJWT(String token) {
    return buildParser().parseSignedClaims(token).getPayload().getSubject();
  }

  public boolean checkTokenValidity(String authToken) {
    try {
      buildParser().parseSignedClaims(authToken);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      log.error("|--> Jwt: Token validation failed: {}", e.getMessage());
      return false;
    }
  }

  private JwtParser buildParser() {
    return Jwts.parser().verifyWith(jwtKey).build();
  }
}
