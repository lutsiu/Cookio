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
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    private final Cloudinary cloudinary;

    @Autowired
    public RecipeServiceImpl(RecipeDAO recipeDAO, CuisineDAO cuisineDAO,
                             TypeDAO typeDAO, UserDAO userDAO, IngredientDAO ingredientDAO) {
        this.recipeDAO = recipeDAO;
        this.cuisineDAO = cuisineDAO;
        this.typeDAO = typeDAO;
        this.userDAO = userDAO;
        this.ingredientDAO = ingredientDAO;

        // Load Cloudinary configuration
        Dotenv dotenv = Dotenv.configure().load();
        String cloudName = dotenv.get("CLOUDINARY_API_NAME");
        String apiKey = dotenv.get("CLOUDINARY_API_KEY");
        String apiSecret = dotenv.get("CLOUDINARY_API_SECRET");

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    @Override
    public RecipeDTOWithUsers createRecipe(Recipe recipe, MultipartFile image) {
        // Step 1: Check for duplicate recipes
        if (isDuplicateRecipe(recipe)) {
            throw new RecipeAlreadyExistsException("Recipe with this data already exists");
        }

        // Step 2: Validate the recipe fields
        validateRecipe(recipe);

        // Step 3: Upload image to Cloudinary
        if (image != null && !image.isEmpty()) {
            try {
                Map<String, Object> uploadResult =
                        cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                System.out.println(uploadResult);
                recipe.setImage((String) uploadResult.get("url"));
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image to Cloudinary", e);
            }
        } else {
            throw new IllegalArgumentException("Image file is missing or invalid");
        }

        // Step 4: Save the recipe
        return RecipeDTOWithUsers.fromRecipe(recipeDAO.save(recipe));
    }


    @Override
    public Optional<RecipeDTONoUser> getRecipeById(int id) {
        return recipeDAO.findById(id)
                .map(RecipeDTONoUser::fromRecipe)
                .or(() -> {
                    throw new RecipeNotFoundException("Recipe not found with id: " + id);
                });
    }

    @Override
    public Optional<RecipeDTONoUser> updateRecipe(int id, Recipe updatedRecipe, MultipartFile image) {
        return Optional.ofNullable(recipeDAO.findById(id).map(existingRecipe -> {
            // Validate the updated recipe
            validateRecipe(updatedRecipe);

            // Update fields
            existingRecipe.setTitle(updatedRecipe.getTitle());
            existingRecipe.setDescription(updatedRecipe.getDescription());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setInstructions(updatedRecipe.getInstructions());

            // Update image (if provided)
            if (image != null && !image.isEmpty()) {
                try {
                    Map<String, Object> uploadResult =
                            cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                    existingRecipe.setImage((String) uploadResult.get("url"));
                } catch (Exception e) {
                    throw new RuntimeException("Failed to update image on Cloudinary", e);
                }
            }

            // Save the updated recipe
            Recipe savedRecipe = recipeDAO.save(existingRecipe);
            return RecipeDTONoUser.fromRecipe(savedRecipe);
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
    public List<RecipeDTONoUser> findRecipesByTitle(String titleKeyword) {
        return recipeDAO.findByTitleContainingIgnoreCase(titleKeyword)
                .stream().map(RecipeDTONoUser::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTONoUser> findRecipesByDescription(String descriptionKeyword) {
        return recipeDAO.findByDescriptionContainingIgnoreCase(descriptionKeyword)
                .stream().map(RecipeDTONoUser::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTONoUser> findRecipesByIngredients(String ingredientsKeyword) {
        return recipeDAO.findByIngredientsContainingIgnoreCase(ingredientsKeyword)
                .stream().map(RecipeDTONoUser::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTONoUser> findRecipesByAuthorId(int authorId) {
        return recipeDAO.findByAuthorId(authorId)
                .stream().map(RecipeDTONoUser::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTONoUser> findRecipesByCategory(String category) {
        return recipeDAO.findByCategoryContainingIgnoreCase(category)
                .stream().map(RecipeDTONoUser::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTONoUser> findRecipesByType(int typeId) {
        return recipeDAO.findByTypeId(typeId)
                .stream().map(RecipeDTONoUser::fromRecipe).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTONoUser> findRecipesByCuisine(int cuisineId) {
        return recipeDAO.findByCuisineId(cuisineId)
                .stream().map(RecipeDTONoUser::fromRecipe).collect(Collectors.toList());
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
