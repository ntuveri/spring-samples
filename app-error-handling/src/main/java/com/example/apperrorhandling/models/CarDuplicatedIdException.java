package com.example.apperrorhandling.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CarDuplicatedIdException extends ResponseStatusException {

    public CarDuplicatedIdException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, null);
    }
}
