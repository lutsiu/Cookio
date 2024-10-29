package com.example.Cookio.controllers.recipe;

import com.example.Cookio.models.Recipe;
import com.example.Cookio.services.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // Endpoint to create a new recipe
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe savedRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.ok(savedRecipe);
    }

    // Endpoint to get all recipes with pagination
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Recipe> recipes = recipeService.getAllRecipes(page, size);
        return ResponseEntity.ok(recipes);
    }

    // Endpoint to get a recipe by ID
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable int id) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to search recipes by title
    @GetMapping("/search/title")
    public ResponseEntity<List<Recipe>> findRecipesByTitle(@RequestParam String title) {
        List<Recipe> recipes = recipeService.findRecipesByTitle(title);
        return ResponseEntity.ok(recipes);
    }

    // Endpoint to search recipes by description
    @GetMapping("/search/description")
    public ResponseEntity<List<Recipe>> findRecipesByDescription(@RequestParam String description) {
        List<Recipe> recipes = recipeService.findRecipesByDescription(description);
        return ResponseEntity.ok(recipes);
    }

    // Endpoint to search recipes by ingredients
    @GetMapping("/search/ingredients")
    public ResponseEntity<List<Recipe>> findRecipesByIngredients(@RequestParam String ingredients) {
        List<Recipe> recipes = recipeService.findRecipesByIngredients(ingredients);
        return ResponseEntity.ok(recipes);
    }

    // Endpoint to get recipes by author ID
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Recipe>> findRecipesByAuthor(@PathVariable int authorId) {
        List<Recipe> recipes = recipeService.findRecipesByAuthorId(authorId);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<Recipe>> findRecipesByCategory(@RequestParam String category) {
        List<Recipe> recipes = recipeService.findRecipesByCategory(category);
        return ResponseEntity.ok(recipes);
    }

    // Find recipes by type
    @GetMapping("/search/type/{typeId}")
    public ResponseEntity<List<Recipe>> findRecipesByType(@PathVariable int typeId) {
        List<Recipe> recipes = recipeService.findRecipesByType(typeId);
        return ResponseEntity.ok(recipes);
    }

    // Find recipes by cuisine
    @GetMapping("/search/cuisine/{cuisineId}")
    public ResponseEntity<List<Recipe>> findRecipesByCuisine(@PathVariable int cuisineId) {
        List<Recipe> recipes = recipeService.findRecipesByCuisine(cuisineId);
        return ResponseEntity.ok(recipes);
    }

    // Endpoint to update an existing recipe by ID
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Optional<Recipe> updatedRecipe = recipeService.updateRecipe(id, recipe);
        return updatedRecipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to delete a recipe by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        boolean deleted = recipeService.deleteRecipe(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
