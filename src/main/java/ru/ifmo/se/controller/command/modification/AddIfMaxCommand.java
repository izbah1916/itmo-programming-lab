package ru.ifmo.se.controller.command.modification;


import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.model.City;
import ru.ifmo.se.service.io.utils.CityReader;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ModificationReceiver;

public class AddIfMaxCommand extends AbstractCommand {
    private final ModificationReceiver modificationReceiver;
    private final CityReader cityReader;


    public AddIfMaxCommand(
            ModificationReceiver modificationReceiver,
            CityReader cityReader,
            BufferedDataWriter infoWriter) {
        super(CommandName.ADD_IF_MAX, CommandDescription.ADD_IF_MAX, false, infoWriter);
        this.modificationReceiver = modificationReceiver;
        this.cityReader = cityReader;
    }

    @Override
    public void runCommand(String param) {
        City newCity = cityReader.readCity();
        if (modificationReceiver.isMax(newCity)) {
            modificationReceiver.add(newCity);
            infoWriter.writeInfo("Element was added to the collection as it is the maximum.");
        } else {
            infoWriter.writeInfo(
                    "Element was not added. It is not greater than the maximum element in the collection.");
        }
    }
}
