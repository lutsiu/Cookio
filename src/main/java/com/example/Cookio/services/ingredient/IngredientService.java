package com.example.Cookio.services.ingredient;

import com.example.Cookio.models.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    Ingredient createIngredient(Ingredient ingredient);

    Optional<Ingredient> getIngredientById(int id);

    List<Ingredient> getAllIngredients();

    List<Ingredient> findIngredientsByName(String name);

    List<Ingredient> findIngredientsByType(String type);

    List<Ingredient> findIngredientsByCaloriesRange(int minCalories, int maxCalories);

    List<Ingredient> findIngredientsByUnit(String unit);

    Optional<Ingredient> updateIngredient(int ingredientId, Ingredient updatedIngredient);

    boolean deleteIngredientById(int id);
}
