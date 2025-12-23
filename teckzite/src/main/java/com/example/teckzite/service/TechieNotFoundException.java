package com.example.teckzite.service;

public class TechieNotFoundException extends RuntimeException{
    public TechieNotFoundException(String s) {
        super(s);
    }
}
