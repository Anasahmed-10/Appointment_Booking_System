package com.appointment.booking.util;

import com.appointment.booking.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms:3600000}") // default 1 hour
    private long jwtExpirationMs;

    private SecretKey key;

 //   private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes()) ;

    @PostConstruct
    public void init() {
        // Initialize SecretKey after spring injects secret
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generate JWT token from User
    public String generateToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name()); 
        return Jwts.builder()
                .subject(user.getEmail())
                .claims(claims)
                .claim("userId", user.getId())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key) // HS256 by default
                .compact();
    }

    // Validate token (basic expiration check)
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key).build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getRoleFromToken(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    // Extract username/email from token
    public String getUsernameFromToken(String token) {
       return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();


    }

    public Object getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}



