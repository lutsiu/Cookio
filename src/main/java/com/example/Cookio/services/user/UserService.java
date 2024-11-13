package com.example.Cookio.services.user;

import com.example.Cookio.dao.user.UserDAO;
import com.example.Cookio.dto.UserDTO;
import com.example.Cookio.exceptions.recipe.RecipeNotFoundException;
import com.example.Cookio.exceptions.user.UserNotFoundException;
import com.example.Cookio.models.Recipe;
import com.example.Cookio.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    UserDTO createUser(User user);

    Optional<UserDTO> getUserById(int id);

    Optional<UserDTO> getUserByEmail(String email);

    List<UserDTO> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<UserDTO> updateUser(int userId, User updatedUser);

    boolean deleteUser(int userId);

    boolean changePassword(int userId, String newPassword);

    void addRecipeToUser(int userId, int recipeId);

    void removeRecipeFromUser(int userId, int recipeId);

    boolean verifyUser(String token);
}

/*

* 1. Basic Authentication and Authorization Setup

2. Email Verification (for Sign-Up)

3. Password Reset

4. OAuth2 for Google and Facebook Sign-In

5. JWT Management and Security Best Practices

* */