package com.jetbrains.libraryservice.exception;

public class NovelaNotFoundException extends RuntimeException {
    public NovelaNotFoundException(String id) {
        super("Novela not found: " + id);
    }
}
