package ru.ifmo.se;

import java.io.File;

/**
 * Entry point of the application.
 * <p>
 * This class initializes and starts the application using a file specified
 * by the environment variable {@code FILE_PATH}.
 * </p>
 */
public class Main {

    /**
     * Main method that starts the application.
     * <p>
     * It checks for the presence of the {@code FILE_PATH} environment variable.
     * If present, it uses the specified file to initialize the data and runs the application.
     * Otherwise, it notifies the user that the required variable is not set.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        String filePath = System.getenv("FILE_PATH");
        if (filePath != null) {
            System.out.println("Database will be loaded from file at: " + filePath);
            Runner runner = new Runner(new File(filePath));
            runner.start();
        } else {
            System.out.println("Environment variable FILE_PATH is not set.");
        }
    }
}
