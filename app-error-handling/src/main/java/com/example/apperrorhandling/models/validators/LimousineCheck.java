package com.example.apperrorhandling.models.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LimousineCheckValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LimousineCheck {
    String acceptedBrands();
    String message() default "The only accepted brands for a limousine are {acceptedBrands}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
