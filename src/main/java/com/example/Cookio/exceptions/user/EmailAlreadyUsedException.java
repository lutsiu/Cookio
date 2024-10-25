package com.example.Cookio.exceptions.user;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("Email " + email + " is already used by another user. Provide another email address");
    }
}
