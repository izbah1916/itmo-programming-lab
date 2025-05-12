package ru.ifmo.se.service.io.writer;

import ru.ifmo.se.service.io.exceptions.RuntimeIOException;
import ru.ifmo.se.service.io.reader.Mode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * A class for writing data to the output using a buffered stream.
 * Allows writing messages to the console in different colors and formats depending on the mode.
 */
public class BufferedDataWriter implements DataWriter {

    /**
     * ANSI code for bright purple text color.
     */
    public static final String ANSI_BRIGHT_PURPLE = "\u001B[95m";

    /**
     * ANSI code for bright cyan text color.
     */
    public static final String ANSI_BRIGHT_CYAN = "\u001B[96m";

    /**
     * ANSI code for resetting the text color to default.
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * ANSI code for bright red text color.
     */
    public static final String ANSI_BRIGHT_RED = "\u001B[91m";

    /**
     * ANSI code for bright yellow text color.
     */
    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";

    /**
     * Buffered writer for output.
     */
    private final BufferedWriter writer;

    /**
     * Mode of operation (console or file).
     */
    private final Mode mode;

    /**
     * Creates a new {@code BufferedDataWriter} instance.
     *
     * @param mode The operation mode (console or file).
     */
    public BufferedDataWriter(Mode mode) {
        this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
        this.mode = mode;
    }

    /**
     * Writes a line without any space at the end.
     *
     * @param line The line to write.
     */
    @Override
    public void writeLineWithoutSpace(String line) {
        try {
            writer.write(line);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeIOException("Error writing line without space", e);
        }
    }

    /**
     * Writes an error message in bright red color.
     *
     * @param message The error message.
     */
    @Override
    public void writeErrorMessage(String message) {
        try {
            writer.write(ANSI_BRIGHT_RED + message + ANSI_RESET);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeIOException("Error writing error message", e);
        }
    }

    /**
     * Writes a regular message in bright purple color if the mode is set to console.
     *
     * @param message The message to write.
     */
    @Override
    public void writeMessage(String message) {
        if (mode.equals(Mode.CONSOLE)) {
            try {
                writer.write(ANSI_BRIGHT_PURPLE + message + ANSI_RESET);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeIOException("Error writing message", e);
            }
        }
    }

    /**
     * Writes an informational message in bright cyan color.
     *
     * @param message The informational message to write.
     */
    @Override
    public void writeInfo(String message) {
        try {
            writer.write(ANSI_BRIGHT_CYAN + message + ANSI_RESET);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeIOException("Error writing message", e);
        }
    }

    /**
     * Writes a debug message in bright yellow color.
     *
     * @param message The debug message to write.
     */
    @Override
    public void writeDebugMessage(String message) {
        try {
            writer.write("---" + ANSI_BRIGHT_YELLOW + message + ANSI_RESET);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeIOException("Error writing message", e);
        }
    }
}
