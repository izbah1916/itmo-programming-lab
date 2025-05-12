package ru.ifmo.se.service.json.writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.ifmo.se.service.json.exceptions.JsonWritingException;
import ru.ifmo.se.service.json.serialization.ClassAdapter;
import ru.ifmo.se.service.json.serialization.DateAdapter;
import ru.ifmo.se.service.json.serialization.LocalDateAdapter;
import ru.ifmo.se.service.json.serialization.LocalDateTimeAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * A class responsible for writing an object to a JSON file.
 * This class utilizes Gson for JSON serialization and registers custom adapters for handling various types.
 *
 * @param <T> The type of the object to serialize into JSON.
 */
public class JsonWriter<T> {

    /**
     * Gson instance used for serialization.
     */
    private final Gson gson;

    /**
     * The type of the object to serialize into JSON.
     */
    private final Type dataType;

    /**
     * Creates a new {@code JsonWriter} instance for the specified type.
     * Registers custom type adapters for LocalDate, LocalDateTime, Class, and Date types.
     * Enables pretty printing and null serialization.
     *
     * @param dataType The type of the object to serialize into JSON.
     */
    public JsonWriter(Type dataType) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Class.class, new ClassAdapter())
                .registerTypeAdapter(Date.class, new DateAdapter())
                .setPrettyPrinting()
                .serializeNulls()  // Ensures null values are serialized
                .create();
        this.dataType = dataType;
    }

    /**
     * Serializes the provided data object into JSON format and writes it to a file.
     *
     * @param data The object to serialize into JSON.
     * @param file The file to write the JSON data to.
     * @throws JsonWritingException If there is an error writing the JSON data to the file.
     */
    public void writeToFile(T data, File file) throws JsonWritingException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            gson.toJson(data, dataType, writer);
        } catch (IOException e) {
            throw new JsonWritingException("Error writing to file", e);
        }
    }
}
