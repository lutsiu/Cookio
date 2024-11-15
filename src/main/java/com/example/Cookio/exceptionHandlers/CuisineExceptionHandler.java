package com.example.Cookio.exceptionHandlers;

import com.example.Cookio.exceptions.cuisine.CuisineAlreadyExistsException;
import com.example.Cookio.exceptions.cuisine.CuisineNotFoundException;
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
public class CuisineExceptionHandler {

    @ExceptionHandler(CuisineAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCuisineAlreadyExists(CuisineAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CuisineNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCuisineNotFound(CuisineNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}