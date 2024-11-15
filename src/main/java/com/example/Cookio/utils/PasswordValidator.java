package com.example.Cookio.utils;

public class PasswordValidator {
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        // REGULAR EXPRESSION CHECKS

        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?.].*");

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }
}
