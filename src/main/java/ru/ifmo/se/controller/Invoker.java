package ru.ifmo.se.controller;

import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.controller.command.CommandManager;
import ru.ifmo.se.controller.exceptions.CommandNotFound;

/**
 * The Invoker class is responsible for invoking commands based on a given input line.
 * It interacts with the CommandManager to retrieve the appropriate command and execute it.
 */
public class Invoker {
    private final CommandManager commandManager;

    /**
     * Constructs an Invoker with the specified CommandManager.
     *
     * @param commandManager The CommandManager that manages available commands.
     */
    public Invoker(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Executes a command based on the given line.
     * The line is parsed to identify the command name and parameters.
     * If the command is found, it is executed with the provided parameters.
     * If the command is not found, a CommandNotFound exception is thrown.
     *
     * @param line The line containing the command to execute.
     * @throws CommandNotFound If the command is not found in the CommandManager.
     */
    public void execute(String line) {
        ParsedCommand parsed = parseCommandLine(line);
        AbstractCommand command = commandManager.getCommand(parsed.commandName);

        if (command == null) {
            throw new CommandNotFound(
                    "Command with name '%s' does not exist. Use 'help' to see available commands.".formatted(
                            parsed.commandName)
            );
        }

        command.execute(parsed.parameters);
    }

    /**
     * Parses the command line into a command name and parameters.
     *
     * @param line The command line to parse.
     * @return A ParsedCommand object containing the command name and parameters.
     * @throws CommandNotFound If the line is empty or null.
     */
    private ParsedCommand parseCommandLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            throw new CommandNotFound("Empty command. Use 'help' to see available commands.");
        }

        String trimmedLine = line.trim();
        int firstSpaceIndex = trimmedLine.indexOf(" ");

        if (firstSpaceIndex == -1) {
            return new ParsedCommand(trimmedLine, null);
        } else {
            String name = trimmedLine.substring(0, firstSpaceIndex);
            String params = trimmedLine.substring(firstSpaceIndex + 1).trim();
            return new ParsedCommand(name, params);
        }
    }

    /**
     * A helper class that holds the parsed command name and parameters.
     */
    private static class ParsedCommand {
        String commandName;
        String parameters;

        /**
         * Constructs a ParsedCommand object with the given command name and parameters.
         *
         * @param commandName The name of the command.
         * @param parameters The parameters of the command.
         */
        ParsedCommand(String commandName, String parameters) {
            this.commandName = commandName;
            this.parameters = parameters;
        }
    }
}
