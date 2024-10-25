package com.example.Cookio.exceptions.user;

public class PasswordTooWeakException extends RuntimeException {
    public PasswordTooWeakException() {
        super("Password is weak." +
                " It must contain at least 1 uppercase letter, " +
                        "1 special character " +
                        "1 number and at least 8 characters length");
    }
}
