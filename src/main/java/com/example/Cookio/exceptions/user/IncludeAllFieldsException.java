package com.example.Cookio.exceptions.user;

public class IncludeAllFieldsException extends RuntimeException {
    public IncludeAllFieldsException() {
        super("Please make sure you've included all fields");
    }
}
