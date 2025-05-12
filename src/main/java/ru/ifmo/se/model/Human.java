package ru.ifmo.se.model;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Human {
    @Max(0)
    private long height; //Значение поля должно быть больше 0
    private java.util.Date birthday;
}