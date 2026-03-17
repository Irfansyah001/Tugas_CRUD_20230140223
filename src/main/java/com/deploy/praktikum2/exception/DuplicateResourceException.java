package com.deploy.praktikum2.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resourceName, String fieldName, String fieldValue) {
        super(resourceName + " dengan " + fieldName + " '" + fieldValue + "' sudah ada");
    }
}
