package com.example.demo.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(Long id) {
        super("Event not found with ID: " + id);
    }
}
