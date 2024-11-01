package com.example.Cookio.controllers.ingredient;

import com.example.Cookio.models.Ingredient;
import com.example.Cookio.models.Ingredient;
import com.example.Cookio.services.ingredient.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/ingredients")
public class IngredientController {
    
    private final IngredientService service;
    
    @Autowired
    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        Ingredient createdIngredient = service.createIngredient(ingredient);
        if (createdIngredient == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIngredient);
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

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        Optional<Ingredient> updatedIngredient = service.updateIngredient(id, ingredient);
        return updatedIngredient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        boolean deleted = service.deleteIngredientById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
