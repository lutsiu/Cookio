package com.example.Cookio.exceptions.cuisine;

public class CuisineAlreadyExistsException extends RuntimeException {

    public CuisineAlreadyExistsException(String message) {
        super(message);
    }
}
