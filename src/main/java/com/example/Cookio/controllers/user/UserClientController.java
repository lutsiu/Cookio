package com.example.Cookio.controllers.user;

import com.example.Cookio.dto.user.UserDTO;
import com.example.Cookio.models.User;
import com.example.Cookio.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*!!! DON'T FORGET TO ADD EXCEPTIONS FOR UNAUTHORIZED USERS and INVALID PATHS*/
@RestController
@RequestMapping("/api/client/users")
public class UserClientController {

    private  UserService userService;

    @Autowired
    public UserClientController(UserService userService) {
        this.userService = userService;
    }

    // untested
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.userId")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        Optional<UserDTO> updated = userService.updateUser(id, updatedUser);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // tested
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.userId")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    // tested
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.email")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<UserDTO> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // untested
    @PatchMapping("/{id}/change-password")
    @PreAuthorize("#id == authentication.principal.userId")
    public ResponseEntity<String> changePassword(
            @PathVariable int id,
            @RequestParam String newPassword) {
        boolean success = userService.changePassword(id, newPassword);
        if (success) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // untested
    @PatchMapping("/{userId}/recipe/{recipeId}/add")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    public ResponseEntity<Void> addRecipeToUser(@PathVariable int userId, @PathVariable int recipeId) {
        userService.addRecipeToUser(userId, recipeId);
        return ResponseEntity.noContent().build();
    }


    // untested
    @PatchMapping("/{userId}/recipe/{recipeId}/remove")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    public ResponseEntity<Void> removeRecipeFromUser(@PathVariable int userId, @PathVariable int recipeId) {
        userService.removeRecipeFromUser(userId, recipeId);
        return ResponseEntity.noContent().build();
    }

}
