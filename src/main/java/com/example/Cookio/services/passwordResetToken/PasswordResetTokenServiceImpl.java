package com.example.Cookio.services.passwordResetToken;

import com.example.Cookio.dao.passwordResetToken.PasswordResetTokenDAO;
import com.example.Cookio.dao.user.UserDAO;
import com.example.Cookio.exceptions.jwt.ExpiredTokenException;
import com.example.Cookio.exceptions.jwt.InvalidTokenException;
import com.example.Cookio.exceptions.user.PasswordTooWeakException;
import com.example.Cookio.exceptions.user.UserNotFoundException;
import com.example.Cookio.models.PasswordResetToken;
import com.example.Cookio.models.User;
import com.example.Cookio.services.email.EmailService;
import com.example.Cookio.utils.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenDAO passwordResetTokenDAO;
    private final UserDAO userDAO;
    private final EmailService emailService;
    private final PasswordEncoder encoder;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenDAO passwordResetTokenDAO,
                                         UserDAO userDAO,
                                         EmailService emailService,
                                         PasswordEncoder encoder) {
        this.passwordResetTokenDAO = passwordResetTokenDAO;
        this.userDAO = userDAO;
        this.emailService = emailService;
        this.encoder = encoder;
    }

    @Override
    public void generateResetToken(String email) {
        User user = userDAO.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException
                        ("User with email " + email + " wasn't found"));

        // Create a reset token
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setExpiration(LocalDateTime.now().plusHours(1)); // Token expires in 1 hour
        resetToken.setUser(user);

        passwordResetTokenDAO.save(resetToken);

        // Send the reset email
        String resetLink = "http://localhost:8080/api/password-reset/verify-token?token=" + resetToken.getToken();
        emailService.sendEmail(user.getEmail(), "Password Reset",
                "Click the link to reset your password: " + resetLink);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = validateAndFetchToken(token);

        // check if password is strong enough
        if (!PasswordValidator.isPasswordStrong(newPassword)) {
            throw new PasswordTooWeakException();
        }

        User user = resetToken.getUser();
        user.setPassword(encoder.encode(newPassword));
        userDAO.save(user);
        // After password reset, delete the token to prevent reuse
        passwordResetTokenDAO.delete(resetToken);
    }

    @Override
    public void verifyToken(String token) {
        validateAndFetchToken(token);
    }

    private PasswordResetToken validateAndFetchToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenDAO.findByToken(token)
                .orElseThrow(InvalidTokenException::new);

        // Check if the token has expired
        if (resetToken.getExpiration().isBefore(LocalDateTime.now())) {
            throw new ExpiredTokenException();
        }

        return resetToken;
    }
}
