package ru.ifmo.se.service.io.exceptions;

import java.io.IOException;

/**
 * Wrapper for checked {@link IOException} to avoid explicit throws declaration.
 */
public class RuntimeIOException extends RuntimeException {
    public RuntimeIOException(String message, Throwable cause) {
        super(message, cause);
    }
}