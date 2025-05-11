package ru.ifmo.se.controller.command;

import lombok.Data;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;

/**
 * Abstract base class for commands, implementing the Command pattern.
 * Each command has a name, description, and may require an argument.
 *
 * <p>Subclasses must implement the {@link #runCommand(String)} method to define command behavior.</p>
 */
@Data
public abstract class AbstractCommand {
    private final String name;
    private final String description;
    private final boolean requiresArgument;
    protected final BufferedDataWriter infoWriter;

    /**
     * Executes the command, checking if an argument is required.
     *
     * @param arg the argument for the command
     */
    public void execute(String arg) {
        if (requiresArgument && (arg == null || arg.trim().isEmpty())) {
            infoWriter.writeInfo("This command requires a parameter.");
            return;
        }
        runCommand(arg);
    }

    /**
     * Defines the specific behavior of the command.
     *
     * @param param the parameter for the command
     */
    public abstract void runCommand(String param);
}
