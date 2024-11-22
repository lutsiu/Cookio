package com.example.Cookio.exceptions.jwt;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() {
        super("Token has expired!");
    }
}