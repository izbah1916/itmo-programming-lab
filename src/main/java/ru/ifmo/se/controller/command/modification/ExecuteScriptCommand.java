package ru.ifmo.se.controller.command.modification;

import ru.ifmo.se.Runner;
import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.Invoker;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.controller.command.CommandManager;
import ru.ifmo.se.controller.exceptions.RecursionException;
import ru.ifmo.se.service.io.exceptions.RuntimeIOException;
import ru.ifmo.se.service.io.reader.BufferedDataReader;
import ru.ifmo.se.service.io.reader.Mode;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ModificationReceiver;
import ru.ifmo.se.service.receiver.ViewReceiver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class ExecuteScriptCommand extends AbstractCommand {
    private final static Set<String> executedScripts = new HashSet<>();
    private final File dbFilePath;
    private final ModificationReceiver modificationReceiver;
    private final ViewReceiver viewReceiver;


    public ExecuteScriptCommand(File dbFilePath, ModificationReceiver modificationReceiver, ViewReceiver viewReceiver,
                                BufferedDataWriter infoWriter) {
        super(CommandName.EXECUTE_SCRIPT, CommandDescription.EXECUTE_SCRIPT, true, infoWriter);
        this.dbFilePath = dbFilePath;
        this.modificationReceiver = modificationReceiver;
        this.viewReceiver = viewReceiver;

    }

    @Override
    public void runCommand(String param) {
        File script = new File(param);
        if (!script.exists()) {
            infoWriter.writeErrorMessage("File does not exist: " + param);
            return;
        }
        if (!script.canRead()) {
            infoWriter.writeErrorMessage("No read permissions for the file: " + param);
            return;
        }

        if (!executedScripts.contains(param)) {
            executedScripts.add(param);
            infoWriter.writeInfo("Starting to execute the script from the file at path %s".formatted(param));
            long start = System.currentTimeMillis();
            try {
                Runner runner = getRunner(script);
                runner.runProcess();
                long end = System.currentTimeMillis();
                String duration = formatDuration(end - start);
                infoWriter.writeMessage(
                        "Execution of the script at path %s completed in %s".formatted(param, duration));
                executedScripts.remove(param);
            } catch (IOException e) {
                throw new RuntimeIOException(e.getMessage(), e);
            }
        } else {
            throw new RecursionException("Do you want to break the system with recursion?");
        }
    }

    private Runner getRunner(File file) throws IOException {
        BufferedDataReader dataReader = new BufferedDataReader(new BufferedReader(new FileReader(file)));
        BufferedDataWriter dataWriter = new BufferedDataWriter(Mode.FILE);
        CommandManager commandManager =
                new CommandManager(modificationReceiver, viewReceiver, dataReader, dataWriter, dbFilePath, Mode.FILE);
        Invoker invoker = new Invoker(commandManager);
        return new Runner(invoker, dataReader, dataWriter, dbFilePath, Mode.FILE);
    }

    private String formatDuration(long durationMillis) {
        Duration duration = Duration.ofMillis(durationMillis);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        long millis = duration.toMillisPart();
        return String.format("%d hours, %d minutes, %d seconds, %d milliseconds", hours, minutes, seconds, millis);
    }
}
