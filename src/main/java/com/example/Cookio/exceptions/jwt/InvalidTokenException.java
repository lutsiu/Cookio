package com.example.Cookio.exceptions.jwt;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Invalid token!");
    }
}