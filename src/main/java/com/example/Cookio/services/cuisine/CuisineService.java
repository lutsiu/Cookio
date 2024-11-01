package com.example.Cookio.services.cuisine;

import com.example.Cookio.models.Cuisine;

import java.util.List;
import java.util.Optional;

public interface CuisineService {
    Cuisine createCuisine(Cuisine cuisine);
    List<Cuisine> getAllCuisines();
    List<Cuisine> getCuisineByName(String cuisine);
    Optional<Cuisine> getCuisineById(int id);
    Optional<Cuisine> updateCuisine(int cuisineId, Cuisine updatedCuisine);
    boolean deleteCuisine(int cuisineId);
}
