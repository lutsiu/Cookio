package com.example.Cookio.exceptions.jwt;

public class InvalidIssuerException extends RuntimeException {
    public InvalidIssuerException(String issuer) {
        super("Issuer " + issuer + " is invalid");
    }
}