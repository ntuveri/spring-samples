package com.example.apperrorhandling.services;

import com.example.apperrorhandling.models.Car;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface CarService {

    Iterable<Car> findAll();

    Car findById(int id);

    Car save(@Valid Car car) throws BindException;
}
