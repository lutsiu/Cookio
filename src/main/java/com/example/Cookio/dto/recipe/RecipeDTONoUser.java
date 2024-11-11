package com.example.Cookio.dto.recipe;

import com.example.Cookio.models.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeDTONoUser {
    private int id;
    private String title;
    private String description;
    private String image;
    private int prepTime;
    private int cookTime;
    private int servings;
    private String category;


    public static RecipeDTONoUser fromRecipe(Recipe recipe) {
        return new RecipeDTONoUser(
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
