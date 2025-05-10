package com.example;
 
public class TutorNotFoundException extends RuntimeException {
    public TutorNotFoundException(String message) {
        super(message);
    }
} 