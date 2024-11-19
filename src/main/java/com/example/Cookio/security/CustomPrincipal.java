package com.example.Cookio.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Data
@Getter
@Setter
public class CustomPrincipal {
    private final int userId;
    private final String email;
}
