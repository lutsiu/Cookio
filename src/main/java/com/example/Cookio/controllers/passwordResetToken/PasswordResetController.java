package com.example.Cookio.controllers.passwordResetToken;

import com.example.Cookio.services.passwordResetToken.PasswordResetTokenService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetTokenService passwordResetService;

    // Step 1: User requests password reset (generates a token and sends the reset email)
    @PostMapping("/request")
    public ResponseEntity<String> requestPasswordReset(@RequestParam @Email @NotBlank String email) {
        passwordResetService.generateResetToken(email);
        return ResponseEntity.ok("Password reset email sent successfully.");

    }

    // Step 2: User clicks the link to verify the token and then proceeds to reset password
    @GetMapping("/verify-token")
    public ResponseEntity<String> verifyResetToken(@RequestParam @NotBlank String token) {
        passwordResetService.verifyToken(token);
        return ResponseEntity.ok("Token is valid. You can now reset your password.");

    }

    // Step 3: User submits a new password with a valid token to reset the password
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam @NotBlank String token,
                                                @RequestParam @NotBlank String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password has been reset successfully.");
    }
}
