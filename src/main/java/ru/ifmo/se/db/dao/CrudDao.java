package ru.ifmo.se.db.dao;

import ru.ifmo.se.db.data.DatabaseDump;
import ru.ifmo.se.db.data.DatabaseMetaData;

import java.util.Collection;
import java.util.Optional;

/**
 * The CrudDao interface provides the basic CRUD (Create, Read, Update, Delete) operations
 * for working with a collection of objects in a database. It defines methods for creating, updating,
 * finding, and removing elements, as well as retrieving metadata and dumps of the database.
 *
 * @param <T> The type of the objects managed by this DAO.
 */
public interface CrudDao<T> {

    /**
     * Creates a new element in the database.
     *
     * @param element The element to be created in the database.
     */
    void create(T element);

    /**
     * Updates an existing element in the database.
     *
     * @param element The element with updated information.
     * @return {@code true} if the update was successful, {@code false} otherwise.
     */
    boolean update(T element);

    /**
     * Finds an element by its unique identifier (ID).
     *
     * @param id The unique identifier of the element.
     * @return An {@code Optional} containing the element if found, or empty if not found.
     */
    Optional<T> findById(long id);

    /**
     * Removes an element by its unique identifier (ID).
     *
     * @param id The unique identifier of the element to remove.
     * @return {@code true} if the element was successfully removed, {@code false} otherwise.
     */
    boolean removeById(long id);

    /**
     * Clears all elements from the database.
     */
    void clear();

    /**
     * Counts the total number of elements in the database.
     *
     * @return The number of elements in the database.
     */
    long count();

    /**
     * Retrieves all elements from the database.
     *
     * @return A collection of all elements in the database.
     */
    Collection<T> getAll();

    /**
     * Retrieves a dump of the entire database, including metadata and data.
     *
     * @return A {@code DatabaseDump} containing the metadata and list of elements.
     */
    DatabaseDump<T> getDump();

    /**
     * Retrieves metadata about the database, including the class type of the records,
     * timestamp of the database dump, and the size of the database.
     *
     * @return A {@code DatabaseMetaData} object containing the metadata information.
     */
    DatabaseMetaData getMetaData();
}
