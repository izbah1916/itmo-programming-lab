package ru.ifmo.se.db.dao;

import ru.ifmo.se.db.data.DatabaseDump;
import ru.ifmo.se.db.data.DatabaseMetaData;
import ru.ifmo.se.db.utils.IdManager;
import ru.ifmo.se.model.City;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

public class CityDao implements CrudDao<City> {
    private final LinkedList<City> list;
    private final IdManager idManager;
    private final DatabaseMetaData databaseMetaData;
    private final DatabaseDump<City> databaseDump;

    public CityDao(DatabaseDump<City> databaseDump) {
        this.list = databaseDump.getList();
        this.databaseDump = databaseDump;
        this.databaseMetaData = databaseDump.getDatabaseMetaData();
        this.idManager = createIdManager(list);
    }
    
    @Override
    public void create(City element) {
        element.setId(idManager.getAvailableId());
        element.setCreationDate(new Date());
        list.offer(element);
    }

    @Override
    public boolean update(City element) {
        Optional<City> optionalCity = findById(element.getId());
        if (optionalCity.isPresent()) {
            list.removeIf(city -> city.getId() == element.getId());
            list.offer(element);
            return true;
        }
        return false;
    }

    @Override
    public Optional<City> findById(long id) {
        return list.stream()
                .filter(city -> city.getId() == id)
                .findFirst();
    }

    @Override
    public boolean removeById(long id) {

        return list.removeIf(city -> {
            if (city.getId() == id) {
                idManager.removeId(id);
                return true;
            }
            return false;
        });
    }

    @Override
    public void clear() {
        list.clear();
        idManager.clear();
    }

    @Override
    public long count() {
        return list.size();
    }

    @Override
    public Collection<City> getAll() {
        return list;
    }

    @Override
    public DatabaseDump<City> getDump() {
        databaseDump.getDatabaseMetaData().setSize(count());
        return databaseDump;
    }

    @Override
    public DatabaseMetaData getMetaData() {
        databaseMetaData.setSize(count());
        return databaseMetaData;
    }

    private IdManager createIdManager(LinkedList<City> queue) {
        return new IdManager(queue.stream().map(City::getId).collect(Collectors.toList()));
    }
}
