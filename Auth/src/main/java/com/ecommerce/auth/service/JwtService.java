package com.ecommerce.auth.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtService {

    @Value("${auth.api-security.jwt.secret}")
    private String jwtSecret;

    @Value("${auth.api-security.jwt.expiration-ms}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream().map(Object::toString).toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return Jwts.builder()
                .issuer("RoadRunner")
                .subject(authentication.getName())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + this.jwtExpirationMs))
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8))).build().parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8))).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public String getAuthFromJwtToken(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8))).build()
                .parseSignedClaims(token).getPayload().get("roles", String.class);
    }
}
