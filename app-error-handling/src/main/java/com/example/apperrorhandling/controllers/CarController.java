package com.example.apperrorhandling.controllers;

import com.example.apperrorhandling.models.Car;
import com.example.apperrorhandling.models.validators.SuvCheckValidator;
import com.example.apperrorhandling.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("cars")
@Validated
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new SuvCheckValidator());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Car> getCars() {
        return carService.findAll();
    }


    @GetMapping(value = "/path", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Car> getCarsPathVariable(@PathVariable(value = "required") Boolean required) {
        return carService.findAll();
    }

    @GetMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Car> getCarsRequestParam(@RequestParam(value = "required") Boolean required) {
        return carService.findAll();
    }

    @GetMapping(value = "/header", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Car> getCarsRequestHeader(@RequestHeader(value = "required") Boolean required) {
        return carService.findAll();
    }

    @GetMapping(value = "/exception", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Car> getCarsException() {
        ((String) null).toString();
        return carService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Car getCar(@PathVariable(value = "id") int id) {
        return carService.findById(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Car addCarController(@Valid @RequestBody Car car) throws BindException {
        return carService.save(car);
    }

    @PostMapping(value = "/service",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Car addCarService(@RequestBody Car car) throws BindException {
        return carService.save(car);
    }
}