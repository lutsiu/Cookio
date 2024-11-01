package com.example.Cookio.exceptions.ingredient;

public class IngredientAlreadyExistsException extends RuntimeException {

    public IngredientAlreadyExistsException(String message) {
        super(message);
    }
}
