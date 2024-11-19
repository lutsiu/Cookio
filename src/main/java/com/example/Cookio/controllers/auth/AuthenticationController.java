package com.example.Cookio.controllers.auth;

import com.example.Cookio.dto.login.LoginRequestDTO;
import com.example.Cookio.dto.user.UserDTO;
import com.example.Cookio.models.User;
import com.example.Cookio.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService service;

    @Autowired
    public AuthenticationController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    private ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        UserDTO createdUser = service.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    private ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        String token = service.loginUser(loginRequest);

        Map<String, String> response = new HashMap<>();
        response.put("jwt", token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    private ResponseEntity<String> verifyUser(@RequestParam("token") String token) {
        boolean isVerified = service.verifyUser(token);
        return isVerified
                ? ResponseEntity.ok("Email verified successfully!")
                : ResponseEntity.badRequest().body("Verification failed. Invalid token.");
    }


}
