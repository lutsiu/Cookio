package com.example.Cookio.services.recipe;

import com.example.Cookio.models.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    // Create a new recipe
    Recipe createRecipe(Recipe recipe);

    // Retrieve a recipe by ID
    Optional<Recipe> getRecipeById(int id);

    // Update an existing recipe
    Recipe updateRecipe(int id, Recipe updatedRecipe);

    // Delete a recipe by ID
    boolean deleteRecipe(int id);

    // Find recipes by title with partial match
    List<Recipe> findRecipesByTitle(String titleKeyword);

    // Find recipes by description with partial match
    List<Recipe> findRecipesByDescription(String descriptionKeyword);

    // Find recipes by ingredients with partial match
    List<Recipe> findRecipesByIngredients(String ingredientsKeyword);

    // Find recipes by author ID
    List<Recipe> findRecipesByAuthorId(int authorId);

    // Additional filtering (category, type, cuisine, etc.)
    List<Recipe> findRecipesByCategory(String category);
    List<Recipe> findRecipesByType(int typeId);
    List<Recipe> findRecipesByCuisine(int cuisineId);

    // Retrieve all recipes (with optional pagination)
    List<Recipe> getAllRecipes(int page, int size);
}
