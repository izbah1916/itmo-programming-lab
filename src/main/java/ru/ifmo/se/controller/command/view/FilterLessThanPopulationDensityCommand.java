package ru.ifmo.se.controller.command.view;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.model.City;
import ru.ifmo.se.service.io.utils.CityOutputHelper;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ViewReceiver;

import java.util.Collection;

public class FilterLessThanPopulationDensityCommand extends AbstractCommand {
    private final ViewReceiver viewReceiver;
    private final CityOutputHelper outputHelper;


    public FilterLessThanPopulationDensityCommand(
            ViewReceiver viewReceiver,
            BufferedDataWriter infoWriter,
            CityOutputHelper outputHelper
    ) {
        super(CommandName.FILTER_LESS_THAN_POPULATION_DENSITY, CommandDescription.FILTER_LESS_THAN_POPULATION_DENSITY,
                true, infoWriter);
        this.viewReceiver = viewReceiver;
        this.outputHelper = outputHelper;
    }

    @Override
    public void runCommand(String param) {
        try {
            long populationDensity = Long.parseLong(param);
            Collection<City> filteredCities = viewReceiver.filterLessThanPopulationDensity(populationDensity);

            if (filteredCities.isEmpty()) {
                infoWriter.writeInfo("No cities found with populationDensity less than " + populationDensity + ".");
            } else {
                infoWriter.writeInfo("Cities with populationDensity less than " + populationDensity + ":");
                outputHelper.showCollectionCity(filteredCities);
            }
        } catch (NumberFormatException e) {
            infoWriter.writeErrorMessage(
                    "Invalid argument format. Please provide a numeric value for populationDensity.");
        }
    }
}
