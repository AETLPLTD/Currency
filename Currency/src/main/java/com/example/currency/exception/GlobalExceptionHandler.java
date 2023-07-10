package com.example.currency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArrayList<String>> handleMethodArgsNotValidExceptionHandler(MethodArgumentNotValidException ex)
    {
        ArrayList<String> lis=new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((objectError) ->
        {

            lis.add (objectError.getDefaultMessage());

        });
        return new ResponseEntity<ArrayList<String>>(lis, HttpStatus.BAD_REQUEST);
    }
}
