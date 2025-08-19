package com.stock.api.exception;

public class DuplicateReferenceException extends RuntimeException {
    public DuplicateReferenceException(String message) {
        super(message);
    }
}