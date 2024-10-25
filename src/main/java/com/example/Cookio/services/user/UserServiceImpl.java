package com.example.Cookio.services.user;

import com.example.Cookio.dao.user.UserDAO;
import com.example.Cookio.dto.UserDTO;
import com.example.Cookio.exceptions.user.PasswordTooWeakException;
import com.example.Cookio.exceptions.user.UserAlreadyExistsException;
import com.example.Cookio.exceptions.user.UserNotFoundException;
import com.example.Cookio.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.encoder = encoder;
    }

    // convert to dto model in order to hide sensitive data
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAvatar(),
                user.getBio(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    @Override
    public UserDTO createUser(User user) {
        if (userDAO.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use: " + user.getEmail());
        }

        // Validate password strength
        if (!isPasswordStrong(user.getPassword())) {
            throw new PasswordTooWeakException();
        }

        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userDAO.save(user);
        return convertToDTO(savedUser);
    }

    public Optional<UserDTO> updateUser(int userId, User updatedUser) {
        return userDAO.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    existingUser.setEmail(updatedUser.getEmail());

                    // Update password only if a new one is provided

                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        // Check if password is strong

                        if (!isPasswordStrong(updatedUser.getPassword())) {
                            throw new PasswordTooWeakException();
                        }
                        existingUser.setPassword(encoder.encode(updatedUser.getPassword()));
                    }

                    existingUser.setAvatar(updatedUser.getAvatar());
                    existingUser.setBio(updatedUser.getBio());

                    User savedUser = userDAO.save(existingUser);
                    return convertToDTO(savedUser);
                })
                .or(() -> {
                    throw new UserNotFoundException("User not found with id: " + userId);
                });
    }

    @Override
    public Optional<UserDTO> getUserById(int id) {
        return userDAO.findById(id)
                .map(this::convertToDTO)
                .or(() -> {
                    throw new UserNotFoundException("User not found with id: " + id);
                });
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return userDAO.findByEmail(email)
                .map(this::convertToDTO)
                .or(() -> {
                    throw new UserNotFoundException("User not found with email: " + email);
                });
    }

    @Override
    public List<UserDTO> findByFirstNameAndLastName(String firstName, String lastName) {
        return userDAO.findByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public boolean changePassword(int userId, String newPassword) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (!isPasswordStrong(newPassword)) {
            throw new PasswordTooWeakException();
        }
        user.setPassword(encoder.encode(newPassword));
        userDAO.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(int userId) {
        if (userDAO.existsById(userId)) {
            userDAO.deleteById(userId);
            return true;
        }
        throw new UserNotFoundException("User not found with id: " + userId);
    }

    // additional method to check whether password is strong enough

    private boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        // REGULAR EXPRESSION CHECKS

        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?.].*");

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }


}

/*
*
* Password Reset Functionality:

    Implement a mechanism to reset the password. This can involve generating a secure token sent to the user’s email, which allows them to set a new password.

Email Verification:

    Require users to verify their email address upon registration. This can help ensure the authenticity of user accounts.

Role Management:

    Allow for user roles (like ADMIN, USER) and implement methods to assign or change roles.

Profile Picture Upload:

    Implement functionality to upload and update user profile pictures.

User Activity Logging:

    Log user activities (like last login time, profile updates) for better tracking and auditing.

Account Locking:

    Implement account locking after a certain number of failed login attempts to enhance security.

Enhanced Validation:

    Add more robust validation for user data, such as checking for strong passwords.

Pagination for User Queries:

    If you're fetching multiple users, implement pagination to improve performance.
* */
