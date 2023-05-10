package com.example.drugstore.exceptions;

public class IdObjectIsNull extends Exception{
    public IdObjectIsNull(){
        super("Object provided has null id.");
    }
}
