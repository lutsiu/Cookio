package com.example.Cookio.security;

import com.example.Cookio.exceptions.jwt.InvalidIssuerException;
import com.example.Cookio.exceptions.jwt.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.security.Key;
import java.util.Date;

public class JWT {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 24 hours in ms

    public static String generateToken(Integer userId, String email, String role) {
        // pass user and set id as subject, claim("email", user.getEmail()), etc
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("role", "ROLE_" + role.toUpperCase()) // Add ROLE_ prefix in JWT
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setIssuer("Cookio")
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (!"Cookio".equals(claims.getIssuer())) {
                throw new InvalidIssuerException(claims.getIssuer());
            }

            return claims; // Return full claims for further processing

        } catch (MalformedJwtException ex) {
            throw new MalformedJwtException("Invalid JWT token was provided");
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtException(ex.getHeader(), ex.getClaims(), ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
