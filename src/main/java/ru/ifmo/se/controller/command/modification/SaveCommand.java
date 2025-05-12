package ru.ifmo.se.controller.command.modification;

import com.google.gson.reflect.TypeToken;
import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.db.data.DatabaseDump;
import ru.ifmo.se.model.City;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.json.exceptions.JsonWritingException;
import ru.ifmo.se.service.json.writer.JsonWriter;
import ru.ifmo.se.service.receiver.ModificationReceiver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

public class SaveCommand extends AbstractCommand {
    private final ModificationReceiver modificationReceiver;
    private File dbFile;

    public SaveCommand(ModificationReceiver modificationReceiver, File dbFile, BufferedDataWriter infoWriter) {
        super(CommandName.SAVE, CommandDescription.SAVE, false, infoWriter);
        this.modificationReceiver = modificationReceiver;
        this.dbFile = dbFile;

    }

    @Override
    public void runCommand(String param) {
        if (!dbFile.canWrite()) {
            infoWriter.writeErrorMessage("No write permissions for the file: %s ".formatted(dbFile));
            try {
                dbFile = new File("backupDatabase.json");
                dbFile.createNewFile();
                if (!dbFile.exists()) {
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            DatabaseDump<City> dump = modificationReceiver.getDump();
            Type dataType = new TypeToken<DatabaseDump<City>>() {
            }.getType();
            JsonWriter<DatabaseDump<City>> jsonWriter = new JsonWriter<>(dataType);
            jsonWriter.writeToFile(dump, dbFile);
            if (dbFile.getName().equals("backupDatabase.json")) {
                infoWriter.writeInfo(
                        "We have taken care of your data and saved it in a different file to prevent data loss!");
            }
            infoWriter.writeInfo("Data successfully saved to file: " + dbFile);
        } catch (JsonWritingException e) {
            infoWriter.writeErrorMessage("Error writing data to file: " + e.getMessage());
        }
    }
}
