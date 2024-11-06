package com.example.Cookio.dto;

import com.example.Cookio.models.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor

public class RecipeDTO {
    private int id;
    private String title;
    private String description;
    private String image;
    private int prepTime;
    private int cookTime;
    private int servings;
    private String category;


    public static RecipeDTO fromRecipe(Recipe recipe) {
        return new RecipeDTO(
            recipe.getId(),
            recipe.getTitle(),
            recipe.getDescription(),
            recipe.getImage(),
            recipe.getPrepTime(),
            recipe.getCookTime(),
            recipe.getServings(),
            recipe.getCategory()
        );
    }
}
