package com.example.apperrorhandling.services;

import com.example.apperrorhandling.models.BodyStyle;
import com.example.apperrorhandling.models.Car;
import com.example.apperrorhandling.models.validators.Argument;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.sql.DatabaseMetaData;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarServiceImpl implements CarService {

    private static List<Car> cars = new ArrayList<Car>() {{
        new Car(1, "VW Golf", LocalDateTime.now().minusMonths(6), BodyStyle.Hatchback);
        new Car(2, "VW Westfalia", LocalDateTime.now().minusYears(3), BodyStyle.VAN);
    }};

    @Override
    public Iterable<Car> findAll() {
        return cars;
    }

    @Override
    public Car findById(int id) {
        List<Car> tmpCars = cars.stream().filter(c -> c.getId() == id).collect(Collectors.toList());

        if(tmpCars.size() == 1) {
            return tmpCars.get(0);
        }

        // TODO: handle not found and multiple results
        return null;
    }

    @Override
    public Car save(Car car) throws BindException {
        validate(car);
        cars.add(car);
        return car;
    }

    private void validate(Car car) throws BindException {
        /*
        DataBinder binder = new DataBinder(car);
        binder.addValidators(new UnoEndProductionDateValidator());
        binder.validate();
        BindingResult results = binder.getBindingResult();
        */

        // or
        Errors errors = validateUnoProductionDate(car);
        if(errors.hasErrors()) {
            BindingResult results = new BeanPropertyBindingResult(car, "car");
            results.addAllErrors(errors);
            throw new BindException(results);
        }
    }

    private Errors validateUnoProductionDate(Car car) {
        return validateUnoProductionDate(car, null);
    }

    private Errors validateUnoProductionDate(Car car, Errors results) {
        LocalDateTime unoEndProductionDate =
                LocalDateTime.of(1990, 1, 1, 0,0, 0);

        if (car.getModel().contains("Uno") && car.getProductionDate().isAfter(unoEndProductionDate)) {

            if(results == null) {
                results = new BeanPropertyBindingResult(car, "car");
            }
            Object[] arguments = new Object[] {
                    new Argument("model", car.getModel()),
                    new Argument("productionDate", car.getProductionDate())
            };

            results.reject("InvalidUnoProductionDate", arguments,
                    String.format("The car model %s was only produced until %s", car.getModel(), unoEndProductionDate));


            /*
            results.rejectValue("model", "InvalidUnoProductionDate", arguments,
                    String.format("The car model %s was only produced until %s", car.getModel(), unoEndProductionDate));

            results.rejectValue("productionDate", "InvalidUnoProductionDate", arguments,
                    String.format("The car model %s was only produced until %s", car.getModel(), unoEndProductionDate));
             */
        }

        return results;
    }

    private class UnoEndProductionDateValidator implements org.springframework.validation.Validator {

        @Override
        public boolean supports(Class<?> clazz) {
            return Car.class.equals(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            Car car = (Car) target;
            validateUnoProductionDate(car, errors);
        }
    }
}
