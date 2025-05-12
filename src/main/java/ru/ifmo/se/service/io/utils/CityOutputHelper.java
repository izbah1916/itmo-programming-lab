package ru.ifmo.se.service.io.utils;

import ru.ifmo.se.model.City;
import ru.ifmo.se.model.Human;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A helper class for formatting and displaying city information.
 * Provides methods to display individual cities or a collection of cities.
 */
public class CityOutputHelper {
    private final BufferedDataWriter infoWriter;

    public CityOutputHelper(BufferedDataWriter infoWriter) {
        this.infoWriter = infoWriter;
    }

    /**
     * Displays information about a single city.
     *
     * @param city the city to display
     */
    public void showCity(City city) {
        if (city == null) {
            infoWriter.writeInfo("City is null.");
        } else {
            String result = formatCity(city);
            infoWriter.writeInfo(result);
        }
    }

    /**
     * Displays information about a collection of cities.
     *
     * @param cities the collection of cities to display
     */
    public void showCollectionCity(Collection<City> cities) {
        if (cities.isEmpty()) {
            infoWriter.writeInfo("The city collection is empty.");
        } else {
            String result = cities.stream()
                    .map(this::formatCity)
                    .collect(Collectors.joining(System.lineSeparator()));
            infoWriter.writeInfo(result);
        }
    }

    private String formatCity(City city) {
        String separatorLine = "=".repeat(75);
        StringBuilder formatted = new StringBuilder();
        formatted.append(separatorLine).append(System.lineSeparator())
                .append("ID: ").append(city.getId()).append(System.lineSeparator())
                .append("Name: ").append(city.getName()).append(System.lineSeparator())
                .append("Coordinates: ").append("(x = ").append(city.getCoordinates().getX())
                .append(", y = ").append(city.getCoordinates().getY()).append(")").append(System.lineSeparator())
                .append("Creation date: ").append(city.getCreationDate()).append(System.lineSeparator())
                .append("Area: ").append(city.getArea()).append(System.lineSeparator())
                .append("Population: ").append(city.getPopulation()).append(System.lineSeparator())
                .append("Meters above sea level: ").append(
                        city.getMetersAboveSeaLevel() != null ? city.getMetersAboveSeaLevel() : "Unknown"
                ).append(System.lineSeparator())
                .append("Population density: ").append(city.getPopulationDensity()).append(System.lineSeparator())
                .append("Agglomeration: ").append(
                        city.getAgglomeration() != null ? city.getAgglomeration() : "Unknown"
                ).append(System.lineSeparator())
                .append("Government: ").append(city.getGovernment()).append(System.lineSeparator())
                .append("Governor: ").append(formatGovernor(city.getGovernor())).append(System.lineSeparator())
                .append(separatorLine);
        return formatted.toString();
    }


    private String formatGovernor(Human governor) {
        if (governor == null) {
            return "None";
        }
        return String.format("(height = %d, birthday = %s)",
                governor.getHeight(),
                governor.getBirthday() != null ? governor.getBirthday().toString() : "null");
    }
}
