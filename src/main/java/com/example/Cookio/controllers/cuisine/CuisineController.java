package com.example.Cookio.controllers.cuisine;

import com.example.Cookio.models.Cuisine;
import com.example.Cookio.services.cuisine.CuisineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/cuisines")
public class CuisineController {

    private final CuisineService cuisineService;

    @Autowired
    public CuisineController(CuisineService cuisineService) {
        this.cuisineService = cuisineService;
    }

    @PostMapping("/create")
    public ResponseEntity<Cuisine> createCuisine(@RequestBody Cuisine cuisine) {
        Cuisine createdCuisine = cuisineService.createCuisine(cuisine);
        if (createdCuisine == null) {
            // Return conflict status if the cuisine already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCuisine);
    }

    @GetMapping
    public ResponseEntity<List<Cuisine>> getAllCuisines() {
        List<Cuisine> cuisines = cuisineService.getAllCuisines();
        return ResponseEntity.ok(cuisines);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Cuisine>> getCuisineByName(@RequestParam String name) {
        List<Cuisine> cuisines = cuisineService.getCuisineByName(name);
        return ResponseEntity.ok(cuisines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuisine> getCuisineById(@PathVariable int id) {
        Optional<Cuisine> cuisine = cuisineService.getCuisineById(id);
        return cuisine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuisine> updateCuisine(@PathVariable int id, @RequestBody Cuisine cuisine) {
        Optional<Cuisine> updatedCuisine = cuisineService.updateCuisine(id, cuisine);
        return updatedCuisine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuisine(@PathVariable int id) {
        boolean deleted = cuisineService.deleteCuisine(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
