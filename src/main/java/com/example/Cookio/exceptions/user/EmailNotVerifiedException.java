package com.example.Cookio.exceptions.user;

public class EmailNotVerifiedException extends RuntimeException {

    public EmailNotVerifiedException() {
        super("Please verify your email before logging in");
    }
}
