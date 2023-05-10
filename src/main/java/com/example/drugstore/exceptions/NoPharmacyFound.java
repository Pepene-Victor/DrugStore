package com.example.drugstore.exceptions;

public class NoPharmacyFound extends Exception {
    public NoPharmacyFound(Integer id){
        super("No pharmacy found with id: " + id);
    }
}
