package com.example.apperrorhandling.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CarNotFoundException extends ResponseStatusException {

    public CarNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason, null);
    }
}
