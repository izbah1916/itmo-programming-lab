package ru.ifmo.se.service.io.utils;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.model.City;
import ru.ifmo.se.model.Coordinates;
import ru.ifmo.se.model.Government;
import ru.ifmo.se.model.Human;
import ru.ifmo.se.service.io.exceptions.ScriptDataReadException;
import ru.ifmo.se.service.io.reader.DataReader;
import ru.ifmo.se.service.io.reader.Mode;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Implementation of the CityReader interface, responsible for reading and validating
 * input data for creating a City entity.
 * Handles user input validation and error messages, and ensures correct data formats.
 */
@RequiredArgsConstructor
public class CityReaderImpl implements CityReader {
    private final DataReader reader;
    private final BufferedDataWriter writer;
    private final Mode mode;

    @Override
    public City readCity() {
        String name = readName();
        Coordinates coordinates = readCoordinates();
        int area = readArea();
        Integer population = readPopulation();
        Integer metersAboveSeaLevel = readMetersAboveSeaLevel();
        float populationDensity = readPopulationDensity();
        Long agglomeration = readAgglomeration();
        Government government = readGovernment();
        Human governor = readGovernor();

        return new City(name, coordinates, area, population, metersAboveSeaLevel,
                populationDensity, agglomeration, government, governor);
    }

    private String readName() {
        writer.writeMessage("Enter a name (non-empty string):");
        String name = reader.readLine();
        while (name == null || name.trim().isEmpty()) {
            writer.writeErrorMessage("Name cannot be empty. Please enter a valid name:");
            handleScriptDataReadError();
            name = reader.readLine();
        }
        return name.trim();
    }

    private Coordinates readCoordinates() {
        writer.writeMessage("Enter X coordinate (integer, max 579):");
        long x = readLong();
        while (x > 579) {
            writer.writeErrorMessage("X cannot be greater than 579. Try again:");
            handleScriptDataReadError();
            x = readLong();
        }

        writer.writeMessage("Enter Y coordinate (floating point number, cannot be null):");
        Double y = readNullableDouble();

        return new Coordinates(x, y);
    }

    private int readArea() {
        writer.writeMessage("Enter city area (integer, min 0):");
        int area = readInt();
        while (area < 0) {
            writer.writeErrorMessage("Area cannot be negative. Try again:");
            handleScriptDataReadError();
            area = readInt();
        }
        return area;
    }

    private Integer readPopulation() {
        writer.writeMessage("Enter population (integer > 0, not null):");
        int population = readInt();

        while (population <= 0) {
            writer.writeErrorMessage("Population must be a positive number. Try again:");
            handleScriptDataReadError();
            population = readInt();
        }
        return population;
    }

    private Integer readMetersAboveSeaLevel() {
        writer.writeMessage("Enter meters above sea level (integer, can be null):");
        return readNullableInt(true);
    }

    private float readPopulationDensity() {
        writer.writeMessage("Enter population density (floating point number > 0):");
        float density = readFloat();
        while (density <= 0) {
            writer.writeErrorMessage("Population density must be positive. Try again:");
            handleScriptDataReadError();
            density = readFloat();
        }
        return density;
    }

    private Long readAgglomeration() {
        writer.writeMessage("Enter agglomeration size (integer, can be null):");
        return readNullableLong();
    }

    private Government readGovernment() {
        writer.writeMessage("Enter government type (ARISTOCRACY, ANARCHY, MONARCHY, OLIGARCHY, TOTALITARIANISM):");
        Government gov = null;
        while (gov == null) {
            try {
                String input = reader.readLine();
                if (input == null || input.isBlank()) {
                    writer.writeErrorMessage("Field cannot be empty. Try again:");
                    handleScriptDataReadError();
                    continue;
                }
                gov = Government.valueOf(input.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                writer.writeErrorMessage(
                        "Invalid value. Possible values: ARISTOCRACY, ANARCHY, MONARCHY, OLIGARCHY, TOTALITARIANISM.");
                handleScriptDataReadError();
            }
        }
        return gov;
    }

    private Human readGovernor() {
        writer.writeMessage("Enter governor's height (integer > 0):");
        long height = readLong();
        while (height <= 0) {
            writer.writeErrorMessage("Error: height must be greater than 0. Enter again:");
            handleScriptDataReadError();
            height = readLong();
        }

        writer.writeMessage("Enter governor's birthday (format: yyyy-MM-dd):");
        Date birthday = readDate();

        return new Human(height, birthday);
    }

    private int readInt() {
        while (true) {
            try {
                String input = reader.readLine();
                if (input == null || input.isBlank()) throw new NumberFormatException();
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                writer.writeErrorMessage("Enter a valid integer.");
                handleScriptDataReadError();
            }
        }
    }

    private Integer readNullableInt(boolean allowNull) {
        while (true) {
            String input = reader.readLine();
            if (input == null || input.isBlank()) {
                if (allowNull) return null;
                writer.writeErrorMessage("Field cannot be empty.");
                handleScriptDataReadError();
                continue;
            }
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                writer.writeErrorMessage("Enter a valid integer.");
                handleScriptDataReadError();
            }
        }
    }

    private long readLong() {
        while (true) {
            try {
                String input = reader.readLine();
                if (input == null || input.isBlank()) throw new NumberFormatException();
                return Long.parseLong(input.trim());
            } catch (NumberFormatException e) {
                writer.writeErrorMessage("Enter a valid integer.");
                handleScriptDataReadError();
            }
        }
    }

    private Long readNullableLong() {
        while (true) {
            String input = reader.readLine();
            if (input == null || input.isBlank()) {
                return null;
            }
            try {
                return Long.parseLong(input.trim());
            } catch (NumberFormatException e) {
                writer.writeErrorMessage("Enter a valid integer.");
                handleScriptDataReadError();
            }
        }
    }

    private float readFloat() {
        while (true) {
            try {
                String input = reader.readLine();
                if (input == null || input.isBlank()) throw new NumberFormatException();
                input = input.replace(",", ".");
                return Float.parseFloat(input.trim());
            } catch (NumberFormatException e) {
                writer.writeErrorMessage("Enter a valid floating point number.");
                handleScriptDataReadError();
            }
        }
    }

    private Double readNullableDouble() {
        while (true) {
            String input = reader.readLine();
            if (input == null || input.isBlank()) {
                return null;
            }
            try {
                input = input.replace(",", ".");
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                writer.writeErrorMessage("Enter a valid floating point number.");
                handleScriptDataReadError();
            }
        }
    }

    private Date readDate() {
        while (true) {
            String input = reader.readLine();
            if (input == null || input.isBlank()) {
                writer.writeErrorMessage("Date cannot be empty.");
                handleScriptDataReadError();
                continue;
            }
            try {
                LocalDate localDate = LocalDate.parse(input.trim());

                if (localDate.isAfter(LocalDate.now())) {
                    writer.writeErrorMessage("Date cannot be in the future.");
                    handleScriptDataReadError();
                    continue;
                }

                return java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            } catch (DateTimeParseException e) {
                writer.writeErrorMessage("Invalid date format. Expected format: yyyy-MM-dd.");
                handleScriptDataReadError();
            }
        }
    }


    private void handleScriptDataReadError() {
        if (mode.equals(Mode.FILE)) {
            throw new ScriptDataReadException(
                    "An error occurred while executing the script on line: %d. Please fix invalid data.".formatted(
                            reader.getLastReadLineNumber()));
        }
    }
}
