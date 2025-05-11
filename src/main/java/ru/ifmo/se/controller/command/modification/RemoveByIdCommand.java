package ru.ifmo.se.controller.command.modification;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ModificationReceiver;

public class RemoveByIdCommand extends AbstractCommand {
    private final ModificationReceiver modificationReceiver;


    public RemoveByIdCommand(ModificationReceiver modificationReceiver, BufferedDataWriter infoWriter) {
        super(CommandName.REMOVE_BY_ID, CommandDescription.REMOVE_BY_ID, true, infoWriter);
        this.modificationReceiver = modificationReceiver;

    }

    @Override
    public void runCommand(String param) {
        try {
            long id = Long.parseLong(param);
            boolean removed = modificationReceiver.removeById(id);
            infoWriter.writeInfo(removed ?
                    String.format("Element with id: %d successfully removed", id) :
                    String.format("Element with id: %d does not exist in the collection", id));
        } catch (NumberFormatException e) {
            infoWriter.writeErrorMessage("Error parsing the id. Make sure the id is an integer.");
        }
    }
}
