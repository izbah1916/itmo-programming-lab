package ru.ifmo.se.service.io.writer;

public interface DataWriter {
    void writeLineWithoutSpace(String line);

    void writeErrorMessage(String message);

    void writeMessage(String message);

    void writeInfo(String message);

    void writeDebugMessage(String message);

}
