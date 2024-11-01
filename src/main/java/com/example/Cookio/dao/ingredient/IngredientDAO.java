package com.example.Cookio.dao.ingredient;

import com.example.Cookio.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientDAO extends JpaRepository<Ingredient, Integer> {

        boolean existsByName(String name);
        @Query("SELECT i FROM Ingredient i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        List<Ingredient> findByNameContainingIgnoreCase(@Param("keyword") String keyword);

        @Query("SELECT i FROM Ingredient i WHERE LOWER(i.type) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        List<Ingredient> findByTypeContainingIgnoreCase(@Param("keyword") String keyword);

        List<Ingredient> findByCaloriesBetween(int minCalories, int maxCalories);

        List<Ingredient> findByUnit(String unit);


}
