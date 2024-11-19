package com.example.Cookio.controllers.ingredient;

import com.example.Cookio.models.Ingredient;
import com.example.Cookio.services.ingredient.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client/ingredients")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
// untested
public class IngredientClientController {

    private final IngredientService service;

    @Autowired
    public IngredientClientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = service.getAllIngredients();
        if (ingredients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable int id) {
        Optional<Ingredient> ingredient = service.getIngredientById(id);
        return ingredient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Ingredient>> getIngredientsByName(@RequestParam String name) {
        List<Ingredient> ingredients = service.findIngredientsByName(name);
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/search/type")
    public ResponseEntity<List<Ingredient>> getIngredientsByType(@RequestParam String type) {
        List<Ingredient> ingredients = service.findIngredientsByType(type);
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/search/unit")
    public ResponseEntity<List<Ingredient>> getIngredientsByUnit(@RequestParam String unit) {
        List<Ingredient> ingredients = service.findIngredientsByUnit(unit);
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/search/calories")
    public ResponseEntity<List<Ingredient>> getIngredientByCaloriesBetween(
            @RequestParam int minCalories,
            @RequestParam int maxCalories
    ) {
        List<Ingredient> ingredients = service.findIngredientsByCaloriesRange(minCalories, maxCalories);
        return ResponseEntity.ok(ingredients);
    }


}
