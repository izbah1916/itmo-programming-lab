package ru.ifmo.se.service.io.reader;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.service.io.exceptions.RuntimeIOException;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Reads lines from a buffered input and tracks the line number.
 * Used for script reading to indicate the exact line where an error occurs.
 */
@RequiredArgsConstructor
public class BufferedDataReader implements DataReader {
    private final BufferedReader reader;
    private int lastReadLineNumber = 0;

    /**
     * Reads a line from the input and increments the line number.
     *
     * @return the read line
     * @throws RuntimeIOException if an error occurs during reading
     */
    @Override
    public String readLine() {
        try {
            String line = reader.readLine();
            lastReadLineNumber++;
            return line;
        } catch (IOException e) {
            throw new RuntimeIOException("Error reading line", e);
        }
    }

    /**
     * Returns the last read line number.
     *
     * @return the last read line number
     */
    @Override
    public int getLastReadLineNumber() {
        return lastReadLineNumber;
    }
}
