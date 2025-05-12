package ru.ifmo.se.controller.command.view;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.model.Government;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ViewReceiver;

import java.util.List;

public class PrintFieldDescendingGovernment extends AbstractCommand {
    private final ViewReceiver viewReceiver;


    public PrintFieldDescendingGovernment(ViewReceiver viewReceiver, BufferedDataWriter infoWriter) {
        super(CommandName.PRINT_FIELD_DESCENDING_GOVERNMENT, CommandDescription.PRINT_FIELD_DESCENDING_GOVERNMENT,
                false, infoWriter);
        this.viewReceiver = viewReceiver;
    }

    @Override
    public void runCommand(String param) {
        List<Government> governments = viewReceiver.getGovernmentsDescending();

        if (governments.isEmpty()) {
            infoWriter.writeInfo("No government values found in the collection.");
        } else {
            infoWriter.writeInfo("Government field values in descending order:");
            governments.forEach(gov -> infoWriter.writeInfo("• " + gov));
        }
    }
}
