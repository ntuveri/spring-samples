package com.example.apperrorhandling.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Engine {
    @DecimalMin(value = "0", message = "The engine volume must be positive")
    private double cubicCms;
    private String type;
}
