package com.example.Cookio.controllers.ingredient;

import com.example.Cookio.models.Ingredient;
import com.example.Cookio.services.ingredient.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/ingredients")
@PreAuthorize("hasRole('ADMIN')")
// untested
public class IngredientAdminController {

    private final IngredientService service;

    @Autowired
    public IngredientAdminController(IngredientService service) {
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
