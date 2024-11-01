package com.example.Cookio.services.cuisine;

import com.example.Cookio.dao.cuisine.CuisineDAO;
import com.example.Cookio.exceptions.cuisine.CuisineAlreadyExistsException;
import com.example.Cookio.exceptions.cuisine.CuisineNotFoundException;
import com.example.Cookio.models.Cuisine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuisineServiceImpl implements CuisineService {

    private final CuisineDAO dao;

    @Autowired
    public CuisineServiceImpl(CuisineDAO cuisineDAO) {
        this.dao = cuisineDAO;
    }

    @Override
    public Cuisine createCuisine(Cuisine cuisine) {
        String cuisineName = cuisine.getName();
        if (dao.existsByName(cuisineName)) {
            throw new CuisineAlreadyExistsException("Cuisine with name " + cuisineName + " already exists");
        }
        return dao.save(cuisine);
    }

    @Override
    public List<Cuisine> getAllCuisines() {
        return dao.findAll();
    }

    @Override
    public List<Cuisine> getCuisineByName(String cuisineName) {
        List<Cuisine> foundCuisines = dao.findByNameIgnoreCase(cuisineName);
        if (foundCuisines.isEmpty()) {
            throw new CuisineNotFoundException("No cuisine found with name: " + cuisineName);
        }
        return foundCuisines;
    }

    @Override
    public Optional<Cuisine> getCuisineById(int id) {
        return Optional.ofNullable(dao.findById(id)
                .orElseThrow(() -> new CuisineNotFoundException("Cuisine with id " + id + " is not found")));
    }

    @Override
    public Optional<Cuisine> updateCuisine(int cuisineId, Cuisine updatedCuisine) {
        return Optional.ofNullable(dao.findById(cuisineId).map(existingCuisine -> {
            existingCuisine.setName(updatedCuisine.getName());
            existingCuisine.setDescription(updatedCuisine.getDescription());
            return dao.save(existingCuisine);
        }).orElseThrow(() ->
                new CuisineNotFoundException("Cuisine with id " + cuisineId + " is not found")));
    }

    @Override
    public boolean deleteCuisine(int cuisineId) {
        if (dao.existsById(cuisineId)) {
            dao.deleteById(cuisineId);
            return true;
        }
        throw new CuisineNotFoundException("Cuisine with id " + cuisineId + " is not found");
    }
}
