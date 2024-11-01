package com.example.Cookio.exceptions.cuisine;

public class CuisineNotFoundException extends RuntimeException {

    public CuisineNotFoundException(String message) {
        super(message);
    }
}
