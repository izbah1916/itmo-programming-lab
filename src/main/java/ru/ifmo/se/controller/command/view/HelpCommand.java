package ru.ifmo.se.controller.command.view;

import lombok.Setter;
import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;

public class HelpCommand extends AbstractCommand {
    @Setter
    private String helpManual;


    public HelpCommand(BufferedDataWriter infoWriter) {
        super(CommandName.HELP, CommandDescription.HELP, false, infoWriter);
    }

    @Override
    public void runCommand(String param) {
        infoWriter.writeInfo(helpManual);
    }
}
