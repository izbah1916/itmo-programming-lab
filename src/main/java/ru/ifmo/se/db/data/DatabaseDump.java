package ru.ifmo.se.db.data;

import lombok.Data;

import java.util.LinkedList;

/**
 * The DatabaseDump class is a generic container for storing metadata and data from the database.
 * It contains metadata about the database and a list of the actual database records.
 *
 * @param <T> The type of the data stored in the database.
 */
@Data
public class DatabaseDump<T> {
    /**
     * Metadata about the database, such as class type, timestamp, and size.
     */
    private final DatabaseMetaData databaseMetaData;

    /**
     * A list of data records from the database.
     */
    private final LinkedList<T> list;
}
