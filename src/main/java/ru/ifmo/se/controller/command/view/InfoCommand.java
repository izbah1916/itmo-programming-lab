package ru.ifmo.se.controller.command.view;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.db.data.DatabaseMetaData;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ViewReceiver;

import java.time.format.DateTimeFormatter;

public class InfoCommand extends AbstractCommand {
    private final ViewReceiver viewReceiver;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public InfoCommand(ViewReceiver viewReceiver, BufferedDataWriter infoWriter) {
        super(CommandName.INFO, CommandDescription.INFO, false, infoWriter);
        this.viewReceiver = viewReceiver;
    }

    @Override
    public void runCommand(String param) {
        DatabaseMetaData metaData = viewReceiver.getMetaData();
        infoWriter.writeInfo(formatDatabaseMetaData(metaData));
    }

    private String formatDatabaseMetaData(DatabaseMetaData metaData) {
        StringBuilder sb = new StringBuilder();
        sb.append("Collection type: ").append(metaData.getClazz().getSimpleName()).append(System.lineSeparator());
        sb.append("Creation date: ").append(metaData.getLocalDateTime().format(formatter))
                .append(System.lineSeparator());
        sb.append("Number of elements: ").append(metaData.getSize());
        return sb.toString();
    }
}
