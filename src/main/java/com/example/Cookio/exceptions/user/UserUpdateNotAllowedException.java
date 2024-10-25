package com.example.Cookio.exceptions.user;

public class UserUpdateNotAllowedException extends RuntimeException {
    public UserUpdateNotAllowedException(String message) {
        super(message);
    }
}
