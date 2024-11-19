package com.example.Cookio.controllers.cuisine;

import com.example.Cookio.models.Cuisine;
import com.example.Cookio.services.cuisine.CuisineService;
import com.example.Cookio.services.type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/cuisines")
@PreAuthorize("hasRole('ADMIN')")
// untested
public class CuisineAdminController {

    private final CuisineService cuisineService;

    @Autowired
    public CuisineAdminController(CuisineService cuisineService) {
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
