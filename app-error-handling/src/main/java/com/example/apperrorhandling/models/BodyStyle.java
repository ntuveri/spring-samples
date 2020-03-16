package com.example.apperrorhandling.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BodyStyle {
    SUV("Sport utility vehicle"),
    VAN("Multi-purpose vehicle (people transportation, cargo)"),
    Hatchback("Small utilitarian car"),
    Limousine("Luxury vehicle");

    private final String description;
}
