package com.example.drugstore.controllers;

import com.example.drugstore.exceptions.IdObjectIsNull;
import com.example.drugstore.exceptions.NoPharmacyFound;
import com.example.drugstore.exceptions.NoProductEntryFound;
import com.example.drugstore.exceptions.NoProductFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoProductEntryFound.class)
    public ResponseEntity<String> noProductEntryFound(NoProductEntryFound e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoProductFound.class)
    public ResponseEntity<String> noProductFound(NoProductFound e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoPharmacyFound.class)
    public ResponseEntity<String> noPharmacyFound(NoPharmacyFound e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdObjectIsNull.class)
    public ResponseEntity<String> nullIdObject(IdObjectIsNull e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleException(ResponseStatusException e){
        return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }
}
