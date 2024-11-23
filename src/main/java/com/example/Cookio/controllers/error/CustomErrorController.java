package com.example.Cookio.controllers.error;

import com.example.Cookio.utils.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    @Autowired
    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
        Map<String, Object> attributes = errorAttributes.getErrorAttributes((WebRequest) request, ErrorAttributeOptions.defaults());

        String message = (String) attributes.getOrDefault("message", "Unexpected error");
        int status = (int) attributes.getOrDefault("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        ErrorResponse response = new ErrorResponse(status, message, LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.valueOf(status));
    }
}
