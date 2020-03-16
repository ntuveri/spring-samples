package com.example.apperrorhandling.models.validators;

import com.example.apperrorhandling.models.BodyStyle;
import com.example.apperrorhandling.models.Car;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LimousineCheckValidator
        implements ConstraintValidator<LimousineCheck, Car> {

    private String[] acceptedBrands;
    private String message;

    @Override
    public void initialize(LimousineCheck constraintAnnotation) {
        this.acceptedBrands = StringUtils.split(constraintAnnotation.acceptedBrands(), ",");
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Car car, ConstraintValidatorContext context) {
        // TODO: add metadata about fields involved in the constraint
        if(car.getBodyStyle() != BodyStyle.Limousine) {
            return true;
        }

        for (String acceptedBrand : acceptedBrands) {
            if (car.getModel().contains(acceptedBrand)) {
                return true;
            }
        }

        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode("model")
                .addConstraintViolation();

        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode("bodyStyle")
                .addConstraintViolation();

        return false;
    }
}
