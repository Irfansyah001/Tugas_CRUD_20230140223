package com.deploy.praktikum2.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Integer id) {
        super(resourceName + " dengan id " + id + " tidak ditemukan");
    }
}
