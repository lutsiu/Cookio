package com.example.Cookio.controllers.user;

import com.example.Cookio.dto.UserDTO;
import com.example.Cookio.models.User;
import com.example.Cookio.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    private ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        UserDTO createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    private ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        Optional<UserDTO> updated = userService.updateUser(id, updatedUser);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    private ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<UserDTO> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name")
    private ResponseEntity<List<UserDTO>> getUsersByName(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        List<UserDTO> users = userService.findByFirstNameAndLastName(firstName, lastName);
        return users.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(users);
    }

    @PatchMapping("/{id}/change-password")
    private ResponseEntity<String> changePassword(
            @PathVariable int id,
            @RequestParam String newPassword) {
        boolean success = userService.changePassword(id, newPassword);
        if (success) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{userId}/recipe/{recipeId}/add")
    private ResponseEntity<Void> addRecipeToUser(@PathVariable int userId, @PathVariable int recipeId) {
        userService.addRecipeToUser(userId, recipeId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{userId}/recipe/{recipeId}/remove")
    private ResponseEntity<Void> removeRecipeFromUser(@PathVariable int userId, @PathVariable int recipeId) {
        userService.removeRecipeFromUser(userId, recipeId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable int id) {
        boolean success = userService.deleteUser(id);
        if (success) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
