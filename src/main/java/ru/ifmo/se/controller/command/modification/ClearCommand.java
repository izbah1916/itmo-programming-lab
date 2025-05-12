package ru.ifmo.se.controller.command.modification;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ModificationReceiver;

public class ClearCommand extends AbstractCommand {
    private final ModificationReceiver modificationReceiver;


    public ClearCommand(ModificationReceiver modificationReceiver, BufferedDataWriter infoWriter) {
        super(CommandName.CLEAR, CommandDescription.CLEAR, false, infoWriter);
        this.modificationReceiver = modificationReceiver;
    }

    @Override
    public void runCommand(String param) {
        modificationReceiver.clear();
        infoWriter.writeMessage("Data successfully cleared.");
    }
}
