package ru.ifmo.se.controller.command.modification;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ModificationReceiver;

public class RemoveAllByPopulationDensityCommand extends AbstractCommand {
    private final ModificationReceiver modificationReceiver;

    public RemoveAllByPopulationDensityCommand(ModificationReceiver modificationReceiver,
                                               BufferedDataWriter infoWriter) {
        super(CommandName.REMOVE_ALL_BY_POPULATION_DENSITY, CommandDescription.REMOVE_ALL_BY_POPULATION_DENSITY, false,
                infoWriter);
        this.modificationReceiver = modificationReceiver;
    }

    @Override
    public void runCommand(String param) {
        try {
            long populationDensity = Long.parseLong(param);
            long countRemoved = modificationReceiver.removeAllByPopulationDensity(populationDensity);
            infoWriter.writeInfo(
                    "Number of elements removed with populationDensity = " + populationDensity + ": " + countRemoved);
        } catch (NumberFormatException e) {
            infoWriter.writeErrorMessage(
                    "Invalid argument format. Please provide a numeric value for populationDensity.");
        }
    }
}
