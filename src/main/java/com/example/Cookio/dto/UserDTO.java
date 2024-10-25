package com.example.Cookio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
