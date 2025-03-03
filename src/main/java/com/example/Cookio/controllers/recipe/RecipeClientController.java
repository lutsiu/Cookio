package com.example.Cookio.controllers.recipe;

import com.example.Cookio.dto.recipe.RecipeDTONoUser;
import com.example.Cookio.dto.recipe.RecipeDTOWithUsers;
import com.example.Cookio.services.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client/recipes")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
// UNTESTED
public class RecipeClientController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeClientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTONoUser>> getAllRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<RecipeDTONoUser> recipes = recipeService.getAllRecipesNoIngredientsNoUsers(page, size);
        return ResponseEntity.ok(recipes);
    }

    // Endpoint to get a recipe by ID +
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTONoUser> getRecipeById(@PathVariable int id) {
        Optional<RecipeDTONoUser> recipe = recipeService.getRecipeById(id);
        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to search recipes by title +
    @GetMapping("/search/title")
    public ResponseEntity<List<RecipeDTONoUser>> findRecipesByTitle(@RequestParam String title) {
        List<RecipeDTONoUser> recipes = recipeService.findRecipesByTitle(title);
        return ResponseEntity.ok(recipes);
    }

    // Endpoint to search recipes by description +
    @GetMapping("/search/description")
    public ResponseEntity<List<RecipeDTONoUser>> findRecipesByDescription(@RequestParam String description) {
        List<RecipeDTONoUser> recipes = recipeService.findRecipesByDescription(description);
        return ResponseEntity.ok(recipes);
    }

    // Endpoint to search recipes by ingredients -
    @GetMapping("/search/ingredients")
    public ResponseEntity<List<RecipeDTONoUser>> findRecipesByIngredients(@RequestParam String ingredients) {
        List<RecipeDTONoUser> recipes = recipeService.findRecipesByIngredients(ingredients);
        return ResponseEntity.ok(recipes);
    }
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<RecipeDTONoUser>> findRecipesByAuthor(@PathVariable int authorId) {
        List<RecipeDTONoUser> recipes = recipeService.findRecipesByAuthorId(authorId);
        return ResponseEntity.ok(recipes);
    }

    // +
    @GetMapping("/search/category")
    public ResponseEntity<List<RecipeDTONoUser>> findRecipesByCategory(@RequestParam String category) {
        List<RecipeDTONoUser> recipes = recipeService.findRecipesByCategory(category);
        return ResponseEntity.ok(recipes);
    }

    // Find recipes by type +
    @GetMapping("/search/type/{typeId}")
    public ResponseEntity<List<RecipeDTONoUser>> findRecipesByType(@PathVariable int typeId) {
        List<RecipeDTONoUser> recipes = recipeService.findRecipesByType(typeId);
        return ResponseEntity.ok(recipes);
    }

    // Find recipes by cuisine +
    @GetMapping("/search/cuisine/{cuisineId}")
    public ResponseEntity<List<RecipeDTONoUser>> findRecipesByCuisine(@PathVariable int cuisineId) {
        List<RecipeDTONoUser> recipes = recipeService.findRecipesByCuisine(cuisineId);
        return ResponseEntity.ok(recipes);
    }

}
