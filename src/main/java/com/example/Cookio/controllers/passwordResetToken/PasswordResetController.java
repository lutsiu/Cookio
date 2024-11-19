package com.example.Cookio.controllers.passwordResetToken;

import com.example.Cookio.services.passwordResetToken.PasswordResetTokenService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/client/password-reset")
// UNTESTED
public class PasswordResetController {

    @Autowired
    private PasswordResetTokenService passwordResetService;

    // Step 1: User requests password reset (generates a token and sends the reset email)
    @PostMapping("/request")
    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.email")
    public ResponseEntity<String> requestPasswordReset(@RequestParam @Email @NotBlank String email) {
        passwordResetService.generateResetToken(email);
        return ResponseEntity.ok("Password reset email sent successfully.");

    }

    // Step 2: User clicks the link to verify the token and then proceeds to reset password
    @GetMapping("/verify-token")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> verifyResetToken(@RequestParam @NotBlank String token) {
        passwordResetService.verifyToken(token);
        return ResponseEntity.ok("Token is valid. You can now reset your password.");

    }

    // Step 3: User submits a new password with a valid token to reset the password
    @PostMapping("/reset")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> resetPassword(@RequestParam @NotBlank String token,
                                                @RequestParam @NotBlank String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password has been reset successfully.");
    }
}
