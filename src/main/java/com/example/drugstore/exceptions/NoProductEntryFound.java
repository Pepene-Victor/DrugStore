package com.example.drugstore.exceptions;

public class NoProductEntryFound extends Exception {
    public NoProductEntryFound(Integer id){
        super("No product entry found with id: " + id);
    }
}
