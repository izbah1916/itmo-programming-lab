package ru.ifmo.se.controller.command;

import ru.ifmo.se.controller.command.modification.*;
import ru.ifmo.se.controller.command.view.*;
import ru.ifmo.se.service.io.reader.DataReader;
import ru.ifmo.se.service.io.reader.Mode;
import ru.ifmo.se.service.io.utils.CityOutputHelper;
import ru.ifmo.se.service.io.utils.CityReader;
import ru.ifmo.se.service.io.utils.CityReaderImpl;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ModificationReceiver;
import ru.ifmo.se.service.receiver.ViewReceiver;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The CommandManager class is responsible for managing and executing commands in the application.
 * It stores all available commands and handles their initialization and retrieval based on the command name.
 */
public class CommandManager {
    private final Map<String, AbstractCommand> allCommands = new HashMap<>();
    private final ModificationReceiver modificationReceiver;
    private final ViewReceiver viewReceiver;
    private final CityReader cityReader;
    private final BufferedDataWriter infoWriter;
    private final File dbFilePath;

    /**
     * Constructs a CommandManager with the given parameters.
     *
     * @param modificationReceiver The receiver responsible for modification operations.
     * @param viewReceiver The receiver responsible for view operations.
     * @param dataReader The reader used for reading city data.
     * @param infoWriter The writer used for outputting information.
     * @param dbFilePath The file path for the database.
     * @param mode The mode to be used for reading city data.
     */
    public CommandManager(ModificationReceiver modificationReceiver, ViewReceiver viewReceiver, DataReader dataReader,
                          BufferedDataWriter infoWriter, File dbFilePath, Mode mode) {
        this.modificationReceiver = modificationReceiver;
        this.viewReceiver = viewReceiver;
        this.infoWriter = infoWriter;
        this.dbFilePath = dbFilePath;
        this.cityReader = new CityReaderImpl(dataReader, infoWriter, mode);
        initializeAllCommand();
    }

    /**
     * Retrieves a command by its name.
     *
     * @param nameCommand The name of the command to retrieve.
     * @return The AbstractCommand associated with the given name, or null if no command is found.
     */
    public AbstractCommand getCommand(String nameCommand) {
        return allCommands.get(nameCommand);
    }

    /**
     * Initializes all available commands by creating instances of them
     * and adding them to the allCommands map.
     */
    private void initializeAllCommand() {
        AddCommand add = new AddCommand(modificationReceiver, cityReader, infoWriter);
        ClearCommand clear = new ClearCommand(modificationReceiver, infoWriter);
        ExecuteScriptCommand executeScript =
                new ExecuteScriptCommand(dbFilePath, modificationReceiver, viewReceiver, infoWriter);
        ExitCommand exit = new ExitCommand(infoWriter);
        RemoveByIdCommand removeById = new RemoveByIdCommand(modificationReceiver, infoWriter);
        RemoveHead removeFirst = new RemoveHead(modificationReceiver, infoWriter);
        RemoveAllByPopulationDensityCommand removeAllByPopulationDensityCommand =
                new RemoveAllByPopulationDensityCommand(modificationReceiver, infoWriter);
        SaveCommand save = new SaveCommand(modificationReceiver, dbFilePath, infoWriter);
        UpdateCommand update = new UpdateCommand(modificationReceiver, cityReader, infoWriter);
        HelpCommand helpCommand = new HelpCommand(infoWriter);
        InfoCommand info = new InfoCommand(viewReceiver, infoWriter);
        PrintFieldDescendingGovernment printFieldDescendingGovernment =
                new PrintFieldDescendingGovernment(viewReceiver, infoWriter);
        ShowCommand show = new ShowCommand(viewReceiver, infoWriter);
        AddIfMaxCommand addIfMaxCommand = new AddIfMaxCommand(modificationReceiver, cityReader, infoWriter);
        HeadCommand headCommand = new HeadCommand(viewReceiver, infoWriter);
        FilterLessThanPopulationDensityCommand filterLessThanPopulationDensityCommand =
                new FilterLessThanPopulationDensityCommand(
                        viewReceiver,
                        infoWriter,
                        new CityOutputHelper(infoWriter)
                );

        // Initialize and store commands
        List<AbstractCommand> commands = Stream.of(
                add,
                clear,
                executeScript,
                exit,
                removeById,
                removeFirst,
                removeAllByPopulationDensityCommand,
                save,
                update,
                helpCommand,
                info,
                printFieldDescendingGovernment,
                show,
                filterLessThanPopulationDensityCommand,
                headCommand,
                addIfMaxCommand
        ).toList();
        commands.forEach(command -> allCommands.put(command.getName(), command));
        helpCommand.setHelpManual(prepareHelpManual());
    }

    /**
     * Prepares a manual containing descriptions of all available commands.
     *
     * @return A string containing the names and descriptions of all commands.
     */
    private String prepareHelpManual() {
        return allCommands.entrySet().stream()
                .map(entry -> entry.getKey() + " : " + entry.getValue().getDescription())
                .collect(Collectors.joining("\n"));
    }
}
