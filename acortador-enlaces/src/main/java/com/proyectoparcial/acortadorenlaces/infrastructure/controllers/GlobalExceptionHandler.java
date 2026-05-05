package com.proyectoparcial.acortadorenlaces.infrastructure.controllers;

import com.proyectoparcial.acortadorenlaces.application.services.InvalidDescriptionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDescriptionException.class)
    public ResponseEntity<String> handleInvalidDescription(InvalidDescriptionException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}