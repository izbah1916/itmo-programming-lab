package ru.ifmo.se.controller.command.modification;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ModificationReceiver;

public class RemoveHead extends AbstractCommand {
    private final ModificationReceiver modificationReceiver;

    public RemoveHead(ModificationReceiver modificationReceiver, BufferedDataWriter infoWriter) {
        super(CommandName.REMOVE_HEAD, CommandDescription.REMOVE_HEAD, false, infoWriter);
        this.modificationReceiver = modificationReceiver;
    }

    @Override
    public void runCommand(String param) {
        boolean removed = modificationReceiver.removeFirst();
        infoWriter.writeInfo(removed ?
                "The first element was successfully removed" :
                "The collection is empty");
    }
}
