package com.example.Cookio.controllers.recipe;


import com.example.Cookio.dto.recipe.RecipeDTOWithUsers;
import com.example.Cookio.models.Recipe;
import com.example.Cookio.services.recipe.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeDTOWithUsers> createRecipe(
            @RequestPart("recipe") String recipeJson,
            @RequestPart("image") MultipartFile image) throws JsonProcessingException {

            ObjectMapper objectMapper = new ObjectMapper();
            Recipe recipe = objectMapper.readValue(recipeJson, Recipe.class);

            RecipeDTOWithUsers savedRecipe = recipeService.createRecipe(recipe, image);
            return ResponseEntity.ok(savedRecipe);

    }


    /*
{
  "title": "Prosecco",
  "description": "Just prosecco",
  "instructions": "Bla blha blah ",
  "prepTime": 5,
  "cookTime": 0,
  "servings": 2,
  "category": "Beverage",
  "ingredients": [
    {"id": 109}
  ],
  "author": {"id": 27},
  "type": {"id": 11},
  "cuisine": {"id": 4}
}
*/

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeDTOWithUsers> updateRecipe(
            @PathVariable int id,
            @RequestPart("recipe") String recipeJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Recipe recipe = objectMapper.readValue(recipeJson, Recipe.class);

        Optional<RecipeDTOWithUsers> updatedRecipe = recipeService.updateRecipe(id, recipe, image);
        return updatedRecipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        boolean deleted = recipeService.deleteRecipe(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
