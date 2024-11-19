package com.example.Cookio.controllers.recipe;


import com.example.Cookio.dto.recipe.RecipeDTOWithUsers;
import com.example.Cookio.models.Recipe;
import com.example.Cookio.services.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/recipes")
@PreAuthorize("hasRole('ADMIN')")
// UNTESTED
public class RecipeAdminController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeAdminController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/create")
    public ResponseEntity<RecipeDTOWithUsers> createRecipe(@RequestBody Recipe recipe) {
        RecipeDTOWithUsers savedRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.ok(savedRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTOWithUsers> updateRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Optional<RecipeDTOWithUsers> updatedRecipe = recipeService.updateRecipe(id, recipe);
        return updatedRecipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        boolean deleted = recipeService.deleteRecipe(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
