package com.example.Cookio.dto;

import com.example.Cookio.dto.recipe.RecipeDTONoUser;
import com.example.Cookio.models.Ingredient;
import com.example.Cookio.models.Recipe;
import com.example.Cookio.models.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
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


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Set<RecipeDTONoUser> recipes;

    public static UserDTO fromUser(User user) {
        // Convert the user entity to the UserDTO
        Set<RecipeDTONoUser> recipeDTOs = user.getSavedRecipes().stream()
                .map(RecipeDTONoUser::fromRecipe)  // Convert each Recipe to RecipeDTO
                .collect(Collectors.toSet()); // Collect the converted RecipeDTOs

        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAvatar(),
                user.getBio(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                recipeDTOs
        );
    }
}
