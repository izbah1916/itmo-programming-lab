package ru.ifmo.se.service.io.exceptions;

/**
 * Thrown when the application lacks permission to read a file.
 */
public class FileReadPermissionException extends RuntimeException {
    public FileReadPermissionException(String message) {
        super(message);
    }
}
