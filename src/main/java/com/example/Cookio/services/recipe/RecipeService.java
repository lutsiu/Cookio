package com.example.Cookio.services.recipe;

import com.example.Cookio.dto.recipe.RecipeDTONoUser;
import com.example.Cookio.dto.recipe.RecipeDTOWithUsers;
import com.example.Cookio.models.Recipe;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface RecipeService {
    // Create a new recipe
    RecipeDTOWithUsers createRecipe(Recipe recipe, MultipartFile image);

    // Retrieve a recipe by ID
    Optional<RecipeDTOWithUsers> getRecipeById(int id);

    // Update an existing recipe
    Optional<RecipeDTOWithUsers> updateRecipe(int id, Recipe updatedRecipe, MultipartFile image);

    // Delete a recipe by ID
    boolean deleteRecipe(int id);

    // Find recipes by title with partial match
    List<RecipeDTOWithUsers> findRecipesByTitle(String titleKeyword);

    // Find recipes by description with partial match
    List<RecipeDTOWithUsers> findRecipesByDescription(String descriptionKeyword);

    // Find recipes by ingredients with partial match
    List<RecipeDTOWithUsers> findRecipesByIngredients(String ingredientsKeyword);

    // Find recipes by author ID
    List<RecipeDTOWithUsers> findRecipesByAuthorId(int authorId);

    // Additional filtering (category, type, cuisine, etc.)
    List<RecipeDTOWithUsers> findRecipesByCategory(String category);
    List<RecipeDTOWithUsers> findRecipesByType(int typeId);
    List<RecipeDTOWithUsers> findRecipesByCuisine(int cuisineId);

    // Retrieve all recipes (with optional pagination)
    List<RecipeDTOWithUsers> getAllRecipes(int page, int size);


    public List<RecipeDTONoUser> getAllRecipesNoIngredientsNoUsers(int page, int size);
}
