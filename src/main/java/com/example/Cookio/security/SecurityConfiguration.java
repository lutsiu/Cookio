package com.example.Cookio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


   private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // add middleware in the future

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(c -> {
                try {
                    c.disable()
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/login",
                                        "/register")
                                .permitAll()
                                .anyRequest().authenticated())
                        .formLogin(form -> form
                                .loginPage("/login")
                                .defaultSuccessUrl("/dashboard")
                                .permitAll())
                        .oauth2Login(o -> o
                                .loginPage("/login")
                                .defaultSuccessUrl("/oauth2/success"))
                        .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .permitAll())
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
