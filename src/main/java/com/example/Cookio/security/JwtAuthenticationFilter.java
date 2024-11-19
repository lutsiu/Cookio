package com.example.Cookio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // No JWT present, continue without setting context
        }

        String token = authHeader.substring(7); // Remove "Bearer "

        try {
            Claims claims = JWT.validateToken(token); // Validate and extract claims

            String email = claims.getSubject(); // Extract email
            String role = claims.get("role", String.class); // Extract role (already prefixed with ROLE_)


            // Create authorities based on the role
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(authority));

            System.out.println("Role extracted from JWT: " + role);
            System.out.println("Authorities: " + Collections.singletonList(authority));
            System.out.println(authentication.getAuthorities());
            // Set authentication in security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            // Handle invalid token scenarios
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response); // Continue with the next filter
    }
}
