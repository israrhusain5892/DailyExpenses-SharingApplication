package com.example.ExpenseSharingApp.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionMessageHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception){

        Map<String, String> errors = new HashMap<>();


        // Get all errors
         exception.getBindingResult().getAllErrors().forEach((error)->{
                    String fieldName=((FieldError) error).getField();
                    String message=error.getDefaultMessage();
                    errors.put(fieldName,message);
        });


        return new ResponseEntity<Map<String,String>>(errors, HttpStatus.BAD_REQUEST);
    }

    }

