package ru.ifmo.se.controller.command.modification;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.model.City;
import ru.ifmo.se.service.io.utils.CityReader;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ModificationReceiver;


public class AddCommand extends AbstractCommand {
    private final ModificationReceiver modificationReceiver;
    private final CityReader cityReader;


    public AddCommand(ModificationReceiver modificationReceiver, CityReader cityReader,
                      BufferedDataWriter infoWriter) {
        super(CommandName.ADD, CommandDescription.ADD, false, infoWriter);
        this.modificationReceiver = modificationReceiver;
        this.cityReader = cityReader;
    }

    @Override
    public void runCommand(String param) {
        City city = cityReader.readCity();
        modificationReceiver.add(city);
        infoWriter.writeInfo("Element successfully added");
    }
}
