package ru.ifmo.se.controller.command.modification;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;

public class ExitCommand extends AbstractCommand {

    public ExitCommand(BufferedDataWriter infoWriter) {
        super(CommandName.EXIT, CommandDescription.EXIT, false, infoWriter);
    }

    @Override
    public void runCommand(String param) {
        infoWriter.writeMessage("Goodbye!");
        System.exit(0);
    }
}
