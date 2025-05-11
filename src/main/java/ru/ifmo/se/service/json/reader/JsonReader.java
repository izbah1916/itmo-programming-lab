package ru.ifmo.se.service.json.reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.ifmo.se.service.json.exceptions.JsonReadingException;
import ru.ifmo.se.service.json.serialization.ClassAdapter;
import ru.ifmo.se.service.json.serialization.DateAdapter;
import ru.ifmo.se.service.json.serialization.LocalDateAdapter;
import ru.ifmo.se.service.json.serialization.LocalDateTimeAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * A class responsible for reading JSON data from a file and deserializing it into an object of a specified type.
 * This class utilizes Gson for JSON deserialization and registers custom adapters for handling various types.
 *
 * @param <T> The type of the object to deserialize the JSON into.
 */
public class JsonReader<T> {

    /**
     * Gson instance used for deserialization.
     */
    private final Gson gson;

    /**
     * The type of the object that the JSON data should be deserialized into.
     */
    private final Type dataType;

    /**
     * Creates a new {@code JsonReader} instance for the specified type.
     * Registers custom type adapters for LocalDate, LocalDateTime, Class, and Date types.
     *
     * @param dataType The type of the object to deserialize the JSON into.
     */
    public JsonReader(Type dataType) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Class.class, new ClassAdapter())
                .registerTypeAdapter(Date.class, new DateAdapter())
                .setPrettyPrinting()
                .create();
        this.dataType = dataType;
    }

    /**
     * Reads JSON data from a file and deserializes it into an object of the specified type.
     *
     * @param file The file containing the JSON data.
     * @return The deserialized object of type {@code T}.
     * @throws JsonReadingException If there is an error reading from the file or deserializing the JSON data.
     */
    public T readFromFile(File file) throws JsonReadingException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return gson.fromJson(reader, dataType);
        } catch (IOException e) {
            throw new JsonReadingException("Error reading from file", e);
        }
    }
}
