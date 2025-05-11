package ru.ifmo.se.controller.command.view;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.model.City;
import ru.ifmo.se.service.io.utils.CityOutputHelper;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ViewReceiver;

import java.util.Optional;

public class HeadCommand extends AbstractCommand {
    private final ViewReceiver viewReceiver;
    private final CityOutputHelper cityOutputHelper;

    public HeadCommand(ViewReceiver viewReceiver, BufferedDataWriter infoWriter) {
        super(CommandName.HEAD, CommandDescription.HEAD, false, infoWriter);
        this.viewReceiver = viewReceiver;
        this.cityOutputHelper = new CityOutputHelper(infoWriter);
    }

    @Override
    public void runCommand(String param) {
        Optional<City> firstCity = viewReceiver.findFirst();

        if (firstCity.isPresent()) {
            cityOutputHelper.showCity(firstCity.get());
        } else {
            infoWriter.writeInfo("The collection is empty.");
        }
    }
}
