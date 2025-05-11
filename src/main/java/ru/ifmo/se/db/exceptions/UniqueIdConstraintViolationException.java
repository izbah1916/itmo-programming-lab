package ru.ifmo.se.db.exceptions;

import jakarta.validation.ValidationException;

/**
 * Exception thrown when there are entities with duplicate IDs in the input file.
 */
public class UniqueIdConstraintViolationException extends ValidationException {
    public UniqueIdConstraintViolationException(String message) {
        super(message);
    }
}
