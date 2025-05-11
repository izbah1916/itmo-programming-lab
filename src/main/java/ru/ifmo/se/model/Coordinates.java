package ru.ifmo.se.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinates {
    @Max(579)
    private long x;  //Максимальное значение поля: 579
    @NotNull
    private Double y;  //Поле не может быть null
}



