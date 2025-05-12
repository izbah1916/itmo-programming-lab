package ru.ifmo.se.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class City implements Comparable<City> {
    @Min(0)
    private long id;
    @NotBlank
    private String name;
    @NotNull
    private Coordinates coordinates;
    @NotNull
    private Date creationDate;
    @Min(0)
    private int area;
    @Min(0)
    @NotNull
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    private Integer metersAboveSeaLevel;
    @Min(0)
    private float populationDensity; //Значение поля должно быть больше 0
    private Long agglomeration;
    @NotNull
    private Government government;
    @NotNull
    private Human governor; //Поле не может быть null

    public City(String name, Coordinates coordinates, int area, Integer population, Integer metersAboveSeaLevel,
                float populationDensity, Long agglomeration, Government government, Human governor) {
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.populationDensity = populationDensity;
        this.agglomeration = agglomeration;
        this.government = government;
        this.governor = governor;
    }

    @Override
    public int compareTo(City o) {
        return this.name.compareTo(o.name);
    }
}
