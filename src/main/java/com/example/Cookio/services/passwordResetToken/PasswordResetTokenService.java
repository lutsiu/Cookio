package com.example.Cookio.services.passwordResetToken;

public interface PasswordResetTokenService {

    void generateResetToken(String email);

    void resetPassword(String token, String newPassword);

    void verifyToken(String token);
}
