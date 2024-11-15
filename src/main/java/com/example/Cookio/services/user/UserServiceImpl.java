package com.example.Cookio.services.user;

import com.example.Cookio.dao.recipe.RecipeDAO;
import com.example.Cookio.dao.user.UserDAO;
import com.example.Cookio.dto.login.LoginRequestDTO;
import com.example.Cookio.dto.user.UserDTO;
import com.example.Cookio.exceptions.recipe.RecipeNotFoundException;
import com.example.Cookio.exceptions.user.*;
import com.example.Cookio.models.Recipe;
import com.example.Cookio.models.User;
import com.example.Cookio.services.email.EmailService;
import com.example.Cookio.security.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RecipeDAO recipeDAO;

    private final EmailService emailService;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RecipeDAO recipeDAO,
                           EmailService emailService,
                           PasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.recipeDAO = recipeDAO;
        this.emailService = emailService;
        this.encoder = encoder;
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

        // encode password
        user.setPassword(encoder.encode(user.getPassword()));

        // generate verification token for email

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setEmailVerified(false);

        // save user to database
        User savedUser = userDAO.save(user);

        emailService.sendVerificationEmail(savedUser.getEmail(), token);

        return UserDTO.fromUser(savedUser);
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
                    return UserDTO.fromUser(savedUser);
                })
                .or(() -> {
                    throw new UserNotFoundException("User not found with id: " + userId);
                });
    }

    @Override
    public Optional<UserDTO> getUserById(int id) {
        return userDAO.findById(id)
                .map(UserDTO::fromUser)
                .or(() -> {
                    throw new UserNotFoundException("User not found with id: " + id);
                });
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return userDAO.findByEmail(email)
                .map(UserDTO::fromUser)
                .or(() -> {
                    throw new UserNotFoundException("User not found with email: " + email);
                });
    }

    @Override
    public List<UserDTO> findByFirstNameAndLastName(String firstName, String lastName) {
        return userDAO.findByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(UserDTO::fromUser)
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

    public void addRecipeToUser(int userId, int recipeId) {
        User user = userDAO.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id " + userId + " is not found"));
        Recipe recipe = recipeDAO.findById(recipeId).orElseThrow(() ->
            new RecipeNotFoundException("Recipe with id " + recipeId + " is not found"));
        user.addRecipe(recipe);
        userDAO.save(user);
    }

    public void removeRecipeFromUser(int userId, int recipeId) {
        User user = userDAO.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id " + userId + " is not found"));
        Recipe recipe = recipeDAO.findById(recipeId).orElseThrow(() ->
                new RecipeNotFoundException("Recipe with id " + recipeId + " is not found"));
        user.removeRecipe(recipe);
        userDAO.save(user);
    }

    @Override
    public boolean verifyUser(String token) {
        Optional<User> optionalUser = userDAO.findByVerificationToken(token);

        if (optionalUser.isEmpty()) {
            // User not found
            return false;
        }

        User user = optionalUser.get();

        if (user.isEmailVerified()) {
            // User is already verified
            return false;
        }

        user.setVerificationToken(null);
        user.setEmailVerified(true);
        userDAO.save(user);

        return true;
    }

    @Override
    public String loginUser(LoginRequestDTO loginRequest) {
        Optional<User> optionalUser = userDAO.findByEmail(loginRequest.getEmail());

        if (optionalUser.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        User user = optionalUser.get();

        String rawPassword = loginRequest.getPassword().trim();

        String encodedPassword = user.getPassword().trim();

        // check whether email is verified
        if (!user.isEmailVerified()) {
            throw new EmailNotVerifiedException();
        }

        if (!encoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidCredentialsException();
        }

        return JWT.generateToken(user.getEmail());

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
