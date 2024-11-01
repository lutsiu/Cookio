package com.example.Cookio.services.ingredient;

import com.example.Cookio.dao.ingredient.IngredientDAO;
import com.example.Cookio.exceptions.ingredient.CaloriesAmountException;
import com.example.Cookio.exceptions.ingredient.IngredientAlreadyExistsException;
import com.example.Cookio.exceptions.ingredient.IngredientNotFoundException;
import com.example.Cookio.models.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientDAO dao;

    @Autowired

    public IngredientServiceImpl(IngredientDAO dao) {
        this.dao = dao;
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        String name = ingredient.getName();
        if (dao.existsByName(name)) {
            throw new IngredientAlreadyExistsException("Ingredient with name '" + name + "' already exists.");
        }
        return dao.save(ingredient);
    }

    @Override
    public Optional<Ingredient> getIngredientById(int id) {
        return Optional.ofNullable(dao.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient with ID " + id + " doesn't exist.")));
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return dao.findAll();
    }

    @Override
    public List<Ingredient> findIngredientsByName(String name) {
        return dao.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Ingredient> findIngredientsByType(String type) {
        return dao.findByTypeContainingIgnoreCase(type);
    }

    @Override
    public List<Ingredient> findIngredientsByCaloriesRange(int minCalories, int maxCalories) {
        if (minCalories <= 0 || maxCalories <= 0) {
            throw new CaloriesAmountException("Calories must be greater than 0, regardless of the unit used.");
        }
        if (minCalories > maxCalories) {
            throw new CaloriesAmountException("Your minCalories amount is higher then maxCalories amount");
        }
        return dao.findByCaloriesBetween(minCalories, maxCalories);
    }

    @Override
    public List<Ingredient> findIngredientsByUnit(String unit) {
        return dao.findByUnit(unit);
    }

    @Override
    public Optional<Ingredient> updateIngredient(int ingredientId, Ingredient updatedIngredient) {
        return Optional.ofNullable(dao.findById(ingredientId)
                .map(ingredient -> {
                    ingredient.setType(updatedIngredient.getType());
                    ingredient.setName(updatedIngredient.getName());
                    ingredient.setUnit(updatedIngredient.getUnit());
                    ingredient.setCalories(updatedIngredient.getCalories());
                    return dao.save(ingredient);
                })
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient with ID " + ingredientId + " doesn't exist.")));
    }

    @Override
    public boolean deleteIngredientById(int id) {
        if (dao.existsById(id)) {
            dao.deleteById(id);
            return true;
        }
        throw new IngredientNotFoundException("Ingredient with ID " + id + " doesn't exist.");
    }
}
