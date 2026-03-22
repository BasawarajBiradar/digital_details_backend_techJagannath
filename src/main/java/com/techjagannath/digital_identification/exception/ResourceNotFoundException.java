package com.techjagannath.digital_identification.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AppException {

    public ResourceNotFoundException(String resource, Object id) {
        super(resource + " not found with id: " + id, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(message, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}