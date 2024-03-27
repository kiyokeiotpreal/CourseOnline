package com.example.BaiTest.exceptions;


import com.example.BaiTest.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String >> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fileName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(fileName, message);
        });
        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
    }

}
