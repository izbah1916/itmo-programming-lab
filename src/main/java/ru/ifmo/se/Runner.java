package ru.ifmo.se;

import com.google.gson.reflect.TypeToken;
import jakarta.validation.ValidationException;
import ru.ifmo.se.controller.Invoker;
import ru.ifmo.se.controller.command.CommandManager;
import ru.ifmo.se.controller.exceptions.CommandNotFound;
import ru.ifmo.se.controller.exceptions.RecursionException;
import ru.ifmo.se.db.dao.CityDao;
import ru.ifmo.se.db.data.DatabaseDump;
import ru.ifmo.se.db.data.DatabaseMetaData;
import ru.ifmo.se.db.exceptions.DatabaseLoadingException;
import ru.ifmo.se.db.exceptions.UniqueIdConstraintViolationException;
import ru.ifmo.se.model.City;
import ru.ifmo.se.service.io.exceptions.FileReadPermissionException;
import ru.ifmo.se.service.io.exceptions.RuntimeIOException;
import ru.ifmo.se.service.io.exceptions.ScriptDataReadException;
import ru.ifmo.se.service.io.reader.BufferedDataReader;
import ru.ifmo.se.service.io.reader.DataReader;
import ru.ifmo.se.service.io.reader.Mode;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.json.exceptions.JsonReadingException;
import ru.ifmo.se.service.json.reader.JsonReader;
import ru.ifmo.se.service.receiver.ModificationReceiver;
import ru.ifmo.se.service.receiver.ViewReceiver;
import ru.ifmo.se.service.validation.ValidationService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Main application runner that loads data, validates it, and processes commands.
 */
public class Runner {
    private final DataReader dataReader;
    private final BufferedDataWriter infoWriter;
    private final File dbFile;
    private final Mode mode;
    private Invoker invoker;
    private JsonReader<DatabaseDump<City>> jsonReader;
    private ValidationService validationService;

    /**
     * Initializes runner with all dependencies.
     */
    public Runner(Invoker invoker, BufferedDataReader dataReader, BufferedDataWriter infoWriter, File dbFile,
                  Mode mode) {
        this.invoker = invoker;
        this.dataReader = dataReader;
        this.infoWriter = infoWriter;
        this.dbFile = dbFile;
        this.mode = mode;
    }

    /**
     * Initializes runner with default console I/O.
     */
    public Runner(File dbFile) {
        this.dbFile = dbFile;
        this.mode = Mode.CONSOLE;
        this.jsonReader = new JsonReader<>(new TypeToken<DatabaseDump<City>>() {
        }.getType());
        this.validationService = new ValidationService();
        this.dataReader = new BufferedDataReader(new BufferedReader(new InputStreamReader(System.in)));
        this.infoWriter = new BufferedDataWriter(Mode.CONSOLE);
    }

    /**
     * Starts the application and handles initialization.
     */
    public void start() {
        try {
            initAll();
            infoWriter.writeMessage("Use 'help' to see available commands.");
            runProcess();
        } catch (UniqueIdConstraintViolationException | JsonReadingException | DatabaseLoadingException |
                 FileReadPermissionException e) {
            infoWriter.writeErrorMessage(e.getMessage());
        } catch (ValidationException e) {
            infoWriter.writeErrorMessage(
                    "The input file contains data constraint violations. Please provide valid data.");
            infoWriter.writeErrorMessage(e.getMessage());
        }
    }

    /**
     * Runs the command processing loop.
     */
    public void runProcess() {
        while (true) {
            if (mode.equals(Mode.CONSOLE)) {
                infoWriter.writeLineWithoutSpace("> ");
            }
            try {
                String input = dataReader.readLine();
                if (mode.equals(Mode.FILE)) {
                    if (input == null) break;
                    if (Objects.equals(input, "")) continue;
                    infoWriter.writeDebugMessage(String.format("Executing command from script (line %d): %s",
                            dataReader.getLastReadLineNumber(), input));
                }
                invoker.execute(input);
            } catch (RecursionException e) {
                infoWriter.writeErrorMessage(e.getMessage());
                break;
            } catch (ScriptDataReadException e) {
                infoWriter.writeDebugMessage(e.getMessage());
                break;
            } catch (CommandNotFound | RuntimeIOException e) {
                infoWriter.writeDebugMessage(e.getMessage());
            }
        }
    }


    /**
     * Loads and validates database from file.
     */
    private DatabaseDump<City> loadDatabaseFromFile(File dbFile) throws JsonReadingException {
        if (!dbFile.exists()) {
            try {
                boolean createdNewFile = dbFile.createNewFile();
                if (createdNewFile) {
                    infoWriter.writeInfo(
                            "Database file at path %s does not exist. A new file has been created. \nYou can continue working!"
                                    .formatted(dbFile.getAbsoluteFile()));
                } else {
                    throw new DatabaseLoadingException(
                            "Database file at path %s does not exist. Please specify a correct path.".formatted(
                                    dbFile.getAbsoluteFile()));
                }
                return createDatabaseDump();
            } catch (IOException e) {
                throw new DatabaseLoadingException(
                        "Error occurred while creating new file at path %s".formatted(dbFile.getAbsoluteFile()), e);
            }
        } else {
            if (!dbFile.canRead()) {
                throw new FileReadPermissionException("Unable to read the data file. Please grant read permission.");
            }
            DatabaseDump<City> dump = jsonReader.readFromFile(dbFile);
            if (dump == null) {
                dump = createDatabaseDump();
            }
            if (validationService.hasDuplicatesId(dump.getList())) {
                throw new UniqueIdConstraintViolationException(
                        "The data contains duplicate IDs. Please provide valid data.");
            }
            validationService.validateConstraints(dump.getList());
            return dump;
        }
    }

    /**
     * Creates a new empty database dump.
     */
    private DatabaseDump<City> createDatabaseDump() {
        DatabaseMetaData metaData = DatabaseMetaData.builder()
                .clazz(LinkedList.class)
                .localDateTime(LocalDateTime.now())
                .size(0L)
                .build();
        return new DatabaseDump<>(metaData, new LinkedList<>());
    }

    /**
     * Initializes all components.
     */
    private void initAll() throws JsonReadingException {
        DatabaseDump<City> dump = loadDatabaseFromFile(dbFile);
        CityDao cityDao = new CityDao(dump);
        ModificationReceiver modificationReceiver = new ModificationReceiver(cityDao);
        ViewReceiver viewReceiver = new ViewReceiver(cityDao);
        CommandManager commandManager =
                new CommandManager(modificationReceiver, viewReceiver, dataReader, infoWriter, dbFile, mode);
        invoker = new Invoker(commandManager);
    }
}
