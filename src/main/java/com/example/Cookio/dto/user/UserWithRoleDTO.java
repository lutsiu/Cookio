package com.example.Cookio.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
@Getter
public class UserWithRoleDTO {
    private UserDTO userDTO;
    private String role;
}
