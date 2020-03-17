package com.example.apperrorhandling.models;

import com.example.apperrorhandling.models.validators.LimousineCheck;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@LimousineCheck(acceptedBrands = "Cadillac, Jaguar")
public class Car {

    private int id;
    @NotBlank
    private String model;
    private LocalDateTime productionDate;
    private BodyStyle bodyStyle;
    @Valid
    private Engine engine;

    public Car(int id, @NotBlank String model, LocalDateTime productionDate, BodyStyle bodyStyle) {
        this.id = id;
        this.model = model;
        this.productionDate = productionDate;
        this.bodyStyle = bodyStyle;
    }
}
