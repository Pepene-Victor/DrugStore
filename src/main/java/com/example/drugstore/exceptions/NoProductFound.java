package com.example.drugstore.exceptions;

public class NoProductFound extends Exception{
    public NoProductFound(Integer id) {
        super("No product found with id:" + id);
    }
}
