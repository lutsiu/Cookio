package com.example.Cookio.exceptions.user;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Entered password is invalid. Try another one.");
    }
}
