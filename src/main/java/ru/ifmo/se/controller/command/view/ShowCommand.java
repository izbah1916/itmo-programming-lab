package ru.ifmo.se.controller.command.view;

import ru.ifmo.se.config.CommandDescription;
import ru.ifmo.se.config.CommandName;
import ru.ifmo.se.controller.command.AbstractCommand;
import ru.ifmo.se.model.City;
import ru.ifmo.se.service.io.utils.CityOutputHelper;
import ru.ifmo.se.service.io.writer.BufferedDataWriter;
import ru.ifmo.se.service.receiver.ViewReceiver;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ShowCommand extends AbstractCommand {
    private final ViewReceiver viewReceiver;
    private final CityOutputHelper cityOutputHelper;


    public ShowCommand(ViewReceiver viewReceiver, BufferedDataWriter infoWriter) {
        super(CommandName.SHOW, CommandDescription.SHOW, false, infoWriter);
        this.viewReceiver = viewReceiver;
        this.cityOutputHelper = new CityOutputHelper(infoWriter);
    }

    @Override
    public void runCommand(String param) {
        Collection<City> cities = viewReceiver.findAll();
        cities = cities.stream().sorted(Comparator.comparingLong(City::getId)).collect(Collectors.toList());
        cityOutputHelper.showCollectionCity(cities);
    }
}
