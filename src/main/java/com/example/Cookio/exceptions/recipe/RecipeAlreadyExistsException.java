package com.example.Cookio.exceptions.recipe;

import com.example.Cookio.models.Recipe;

public class RecipeAlreadyExistsException extends RuntimeException {
    public RecipeAlreadyExistsException(String message) {
        super(message);
    }

}
