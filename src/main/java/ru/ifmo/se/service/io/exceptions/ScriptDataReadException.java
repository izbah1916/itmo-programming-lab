package ru.ifmo.se.service.io.exceptions;

/**
 * Thrown when an error occurs while reading data during script execution.
 * Execution stops and the error message specifies the line where the error occurred.
 */
public class ScriptDataReadException extends RuntimeException {
    public ScriptDataReadException(String message) {
        super(message);
    }
}
