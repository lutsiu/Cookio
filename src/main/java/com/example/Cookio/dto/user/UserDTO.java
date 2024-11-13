package com.example.Cookio.dto.user;

import com.example.Cookio.dto.recipe.RecipeDTONoUser;
import com.example.Cookio.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor

public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private String bio;
    private boolean isEmailVerified;
    private String verificationToken;
    private Set<RecipeDTONoUser> recipes;

    public static UserDTO fromUser(User user) {
        // Check if savedRecipes is null or empty before processing
        Set<RecipeDTONoUser> recipeDTOs = (user.getSavedRecipes() == null || user.getSavedRecipes().isEmpty()) ?
                null :
                user.getSavedRecipes().stream()
                        .map(RecipeDTONoUser::fromRecipe)  // Convert each Recipe to RecipeDTO
                        .collect(Collectors.toSet()); // Collect the converted RecipeDTOs

        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAvatar(),
                user.getBio(),
                user.isEmailVerified(),
                user.getVerificationToken(),
                recipeDTOs
        );
    }

}
