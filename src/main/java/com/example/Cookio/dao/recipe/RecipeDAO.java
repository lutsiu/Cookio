package com.example.Cookio.dao.recipe;

import com.example.Cookio.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeDAO extends JpaRepository<Recipe, Integer> {

    // Find recipes by title (partial match, case-insensitive)
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Recipe> findByTitleContainingIgnoreCase(@Param("keyword") String keyword);

    // Find recipes by description (partial match, case-insensitive)
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Recipe> findByDescriptionContainingIgnoreCase(@Param("keyword") String keyword);

    // Find recipes by ingredients (partial match, case-insensitive)
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.ingredients) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Recipe> findByIngredientsContainingIgnoreCase(@Param("keyword") String keyword);

    // Find recipes by category (case-insensitive)
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.category) = LOWER(:category)")
    List<Recipe> findByCategoryIgnoreCase(@Param("category") String category);

    @Query("SELECT r FROM Recipe r WHERE r.author.id = :authorId")
    List<Recipe> findByAuthorId(@Param("authorId") int authorId);

    // Find recipes by preparation time range
    List<Recipe> findByPrepTimeBetween(int minPrepTime, int maxPrepTime);

    // Find recipes by cook time range
    List<Recipe> findByCookTimeBetween(int minCookTime, int maxCookTime);

    // Find recipes by cuisine ID
    List<Recipe> findByCuisineId(int cuisineId);

    // Find recipes by type ID
    List<Recipe> findByTypeId(int typeId);

    // Find recipes by servings
    List<Recipe> findByServings(int servings);

    // Find most recent recipes
    List<Recipe> findTop5ByOrderByCreatedAtDesc();
}
