package com.example.Cookio.security;

import com.example.Cookio.exceptions.jwt.ExpiredTokenException;
import com.example.Cookio.exceptions.jwt.InvalidIssuerException;
import com.example.Cookio.exceptions.jwt.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JWT {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long EXPIRATION_TIME = 86400000; // 24 hours in ms

    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setIssuer("Cookio")
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (!"Cookio".equals(claims.getIssuer())) {
                throw new InvalidIssuerException(claims.getIssuer());
            }
            return claims.getSubject(); // returns email of user

        } catch (ExpiredJwtException e) {
           throw new ExpiredTokenException();

        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }
}
