package com.example.Cookio.controllers.cuisine;

import com.example.Cookio.models.Cuisine;
import com.example.Cookio.services.cuisine.CuisineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client/cuisines")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
// untested
public class CuisineClientController {

    private final CuisineService cuisineService;

    @Autowired
    public CuisineClientController(CuisineService cuisineService) {
        this.cuisineService = cuisineService;
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

}
