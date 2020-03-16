package com.example.apperrorhandling.models.validators;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Argument
{
    private String name;
    private Object value;
}