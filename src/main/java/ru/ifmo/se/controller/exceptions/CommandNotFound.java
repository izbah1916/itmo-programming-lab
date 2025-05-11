package ru.ifmo.se.controller.exceptions;

/**
 * Exception thrown when a command is not found in the system.
 * This typically occurs when an invalid or unknown command is provided.
 */
public class CommandNotFound extends RuntimeException {

    /**
     * Constructs a new CommandNotFound exception with the specified detail message.
     *
     * @param message The detail message that explains the cause of the exception.
     */
    public CommandNotFound(String message) {
        super(message);
    }
}
