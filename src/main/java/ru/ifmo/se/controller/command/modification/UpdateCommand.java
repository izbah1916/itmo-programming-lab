package ru.ifmo.se.controller.command.modification;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.model.City;
import ru.ifmo.se.service.io.utils.CityReader;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ModificationReceiver;

import java.util.Optional;

public class UpdateCommand extends AbstractCommand {
    private final ModificationReceiver modificationReceiver;
    private final CityReader cityReader;


    public UpdateCommand(ModificationReceiver modificationReceiver, CityReader cityReader, BufferedDataWriter infoWriter) {
        super(CommandName.UPDATE, CommandDescription.UPDATE, false, infoWriter);
        this.modificationReceiver = modificationReceiver;
        this.cityReader = cityReader;

    }

    @Override
    public void runCommand(String param) {
        try {
            long id = Long.parseLong(param);
            Optional<City> canBeCity = modificationReceiver.findById(id);

            if (!canBeCity.isPresent()) {
                infoWriter.writeErrorMessage(String.format("Element with id %d does not exist", id));
            } else {
                City city = cityReader.readCity();
                boolean updated = modificationReceiver.update(id, city);
                infoWriter.writeInfo(updated ?
                        String.format("Element with id %d successfully updated", id) :
                        String.format("Element with id %d does not exist in the collection", id));
            }
        } catch (NumberFormatException e) {
            infoWriter.writeErrorMessage("Error parsing the id. Make sure the id is an integer.");
        }
    }
}
