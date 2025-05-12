package ru.ifmo.se.db.data;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The DatabaseMetaData class holds metadata information about a specific database dump.
 * It contains information about the class of the database records, the timestamp when the dump was created,
 * and the size of the database at the time of the dump.
 *
 * This class is used by the `info` command to retrieve and display information about the database, such as
 * the type of records, the time when the database was dumped, and the size of the database.
 */
@Data
@Builder
public class DatabaseMetaData {
    /**
     * The class type of the database records.
     */
    private Class<?> clazz;

    /**
     * The timestamp indicating when the database dump was created.
     */
    private LocalDateTime localDateTime;

    /**
     * The size of the database (in bytes or any other unit).
     */
    private Long size;
}
