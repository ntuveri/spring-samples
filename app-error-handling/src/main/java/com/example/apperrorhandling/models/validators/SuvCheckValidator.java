package com.example.apperrorhandling.models.validators;

import com.example.apperrorhandling.models.BodyStyle;
import com.example.apperrorhandling.models.Car;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;


@Component
public class SuvCheckValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Car.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Car car = (Car) target;
        if (car.getModel().contains("Tiguan") && car.getBodyStyle() != BodyStyle.SUV) {

            errors.rejectValue("model", "InvalidSuv",
                    new Object[] { "one", 2 },
                    "The car model is not compatible with the car body style");

            errors.rejectValue("bodyStyle", "InvalidSuv",
                    new Object[] { 1, "two" },
                    "The car body style is not compatible with the car model");
        }
    }
}
