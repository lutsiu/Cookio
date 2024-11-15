package com.example.Cookio.exceptionHandlers;

import com.example.Cookio.exceptions.ingredient.CaloriesAmountException;
import com.example.Cookio.exceptions.ingredient.IngredientAlreadyExistsException;
import com.example.Cookio.exceptions.ingredient.IngredientNotFoundException;
import com.example.Cookio.utils.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class IngredientsExceptionHandler {
    @ExceptionHandler(IngredientAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleIngredientAlreadyExists(IngredientAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(IngredientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleIngredientNotFound(IngredientNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CaloriesAmountException.class)
    public ResponseEntity<ErrorResponse> handleCaloriesAmountException(CaloriesAmountException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
