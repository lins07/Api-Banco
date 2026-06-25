package com.hexkai.api.banco.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // Status 400
        
        StandardError err = new StandardError(
                Instant.now(),
                status.value(),
                "Requisição Inválida",
                e.getMessage(), 
                request.getRequestURI()
        );
        
        return ResponseEntity.status(status).body(err);
    }
}