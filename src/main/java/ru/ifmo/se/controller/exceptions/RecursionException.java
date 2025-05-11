package ru.ifmo.se.controller.exceptions;

/**
 * Exception thrown when a script attempts to recursively call itself.
 * This indicates a potential infinite recursion or unintended self-call.
 */
public class RecursionException extends RuntimeException {

    /**
     * Constructs a new RecursionException with the specified detail message.
     *
     * @param message The detail message that explains the cause of the exception.
     */
    public RecursionException(String message) {
        super(message);
    }
}
