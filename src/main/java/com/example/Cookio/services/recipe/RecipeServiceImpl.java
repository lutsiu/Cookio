package com.example.Cookio.services.recipe;

import com.example.Cookio.dao.cuisine.CuisineDAO;
import com.example.Cookio.dao.ingredient.IngredientDAO;
import com.example.Cookio.dao.recipe.RecipeDAO;
import com.example.Cookio.dao.type.TypeDAO;
import com.example.Cookio.dao.user.UserDAO;
import com.example.Cookio.dto.recipe.RecipeDTONoUser;
import com.example.Cookio.dto.recipe.RecipeDTOWithUsers;
import com.example.Cookio.exceptions.cuisine.CuisineNotFoundException;
import com.example.Cookio.exceptions.ingredient.IngredientNotFoundException;
import com.example.Cookio.exceptions.recipe.RecipeAlreadyExistsException;
import com.example.Cookio.exceptions.recipe.RecipeNotFoundException;
import com.example.Cookio.exceptions.type.TypeNotFoundException;
import com.example.Cookio.exceptions.user.UserNotFoundException;
import com.example.Cookio.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeDAO recipeDAO;
    private final CuisineDAO cuisineDAO;
    private final TypeDAO typeDAO;
    private final UserDAO userDAO;

    private final IngredientDAO ingredientDAO;

    @Autowired
    public RecipeServiceImpl(RecipeDAO recipeDAO, CuisineDAO cuisineDAO,
                             TypeDAO typeDAO, UserDAO userDAO, IngredientDAO ingredientDAO) {
        this.recipeDAO = recipeDAO;
        this.cuisineDAO = cuisineDAO;
        this.typeDAO = typeDAO;
        this.userDAO = userDAO;
        this.ingredientDAO = ingredientDAO;
    }

    @Override
    public RecipeDTOWithUsers createRecipe(Recipe recipe) {
        System.out.println(recipe.getAuthor().getEmail());
        boolean recipeIsAlreadyCreated = this.isDuplicateRecipe(recipe);


        if (recipeIsAlreadyCreated) {
            throw new RecipeAlreadyExistsException("Recipe with this data already exists");
        }

        validateRecipe(recipe);

        return RecipeDTOWithUsers.fromRecipe(recipeDAO.save(recipe));
    }

    @Override
    public Optional<RecipeDTOWithUsers> getRecipeById(int id) {
        return recipeDAO.findById(id)
                .map(RecipeDTOWithUsers::fromRecipe)
                .or(() -> {
                    throw new RecipeNotFoundException("Recipe not found with id: " + id);
                });
    }


    @Override
    public Optional<RecipeDTOWithUsers> updateRecipe(int id, Recipe updatedRecipe) {
        return Optional.ofNullable(recipeDAO.findById(id).map(existingRecipe -> {
            validateRecipe(updatedRecipe);
            existingRecipe.setTitle(updatedRecipe.getTitle());
            existingRecipe.setDescription(updatedRecipe.getDescription());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setInstructions(updatedRecipe.getInstructions());
            existingRecipe.setImage(updatedRecipe.getImage());
            existingRecipe.setPrepTime(updatedRecipe.getPrepTime());
            existingRecipe.setCookTime(updatedRecipe.getCookTime());
            existingRecipe.setServings(updatedRecipe.getServings());
            existingRecipe.setCategory(updatedRecipe.getCategory());
            existingRecipe.setType(updatedRecipe.getType());
            existingRecipe.setCuisine(updatedRecipe.getCuisine());

            Recipe savedRecipe = recipeDAO.save(existingRecipe);
            return RecipeDTOWithUsers.fromRecipe(savedRecipe);
        }).orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id)));
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipeDAO.existsById(id)) {
            recipeDAO.deleteById(id);
            return true;
        }
        throw new RecipeNotFoundException("Recipe not found with id: " + id);
    }

    @Override
    public List<RecipeDTOWithUsers> findRecipesByTitle(String titleKeyword) {
        return recipeDAO.findByTitleContainingIgnoreCase(titleKeyword)
                .stream().map(RecipeDTOWithUsers::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTOWithUsers> findRecipesByDescription(String descriptionKeyword) {
        return recipeDAO.findByDescriptionContainingIgnoreCase(descriptionKeyword)
                .stream().map(RecipeDTOWithUsers::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTOWithUsers> findRecipesByIngredients(String ingredientsKeyword) {
        return recipeDAO.findByIngredientsContainingIgnoreCase(ingredientsKeyword)
                .stream().map(RecipeDTOWithUsers::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTOWithUsers> findRecipesByAuthorId(int authorId) {
        return recipeDAO.findByAuthorId(authorId)
                .stream().map(RecipeDTOWithUsers::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTOWithUsers> findRecipesByCategory(String category) {
        return recipeDAO.findByCategoryContainingIgnoreCase(category)
                .stream().map(RecipeDTOWithUsers::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTOWithUsers> findRecipesByType(int typeId) {

        return recipeDAO.findByTypeId(typeId)
                .stream().map(RecipeDTOWithUsers::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTOWithUsers> findRecipesByCuisine(int cuisineId) {
        return recipeDAO.findByCuisineId(cuisineId)
                .stream().map(RecipeDTOWithUsers::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTOWithUsers> getAllRecipes(int page, int size) {
        return recipeDAO.findAll(PageRequest.of(page, size)).getContent()
                .stream().map(RecipeDTOWithUsers::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTONoUser> getAllRecipesNoIngredientsNoUsers(int page, int size) {
        return recipeDAO.findAll(PageRequest.of(page, size)).getContent()
                .stream().map(RecipeDTONoUser::fromRecipe).collect(Collectors.toList());
    }

    private boolean isDuplicateRecipe(Recipe recipe) {
        List<Recipe> duplicates = recipeDAO.findDuplicateRecipe(recipe.getTitle(),
                recipe.getInstructions(),
                recipe.getPrepTime(),
                recipe.getCookTime(),
                recipe.getServings(),
                recipe.getCategory());
        return !duplicates.isEmpty();
    }

    private void validateRecipe(Recipe recipe) {
        User author = recipe.getAuthor();
        Cuisine cuisine = recipe.getCuisine();
        Type type = recipe.getType();
        Set<Ingredient> ingredients = recipe.getIngredients();

        ingredients.forEach(ingredient -> {
            if (!ingredientDAO.existsById(ingredient.getId())) {
                throw new IngredientNotFoundException("Ingredient with id " + ingredient.getId() +
                        " doesn't exist");
            }
        });

        if (!userDAO.existsById(author.getId())) {
            throw new UserNotFoundException("Author with id " + author.getId() + " doesn't exist");
        }

        if (!cuisineDAO.existsById(cuisine.getId())) {
            throw new CuisineNotFoundException("Cuisine with id " + cuisine.getId() + " doesn't exist");
        }

        if (!typeDAO.existsById(type.getId())) {
            throw new TypeNotFoundException("Type with id " + type.getId() + " doesn't exist");
        }
    }
}
