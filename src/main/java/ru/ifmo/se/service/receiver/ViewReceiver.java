package ru.ifmo.se.service.receiver;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.db.dao.CrudDao;
import ru.ifmo.se.db.data.DatabaseMetaData;
import ru.ifmo.se.model.City;
import ru.ifmo.se.model.Government;

import java.util.*;

/**
 * Service class responsible for read-only operations on {@link City} entities.
 * Provides methods to retrieve and filter city data from the data source.
 */
@RequiredArgsConstructor
public class ViewReceiver {

    /**
     * Data access object for performing read operations on {@link City}.
     */
    private final CrudDao<City> crudDao;

    /**
     * Retrieves all {@link City} entries from the collection.
     *
     * @return a collection containing all cities
     */
    public Collection<City> findAll() {
        return crudDao.getAll();
    }

    /**
     * Retrieves the first {@link City} from the collection.
     *
     * @return an {@link Optional} containing the first city if present, or empty otherwise
     */
    public Optional<City> findFirst() {
        return crudDao.getAll().stream().findFirst();
    }

    /**
     * Filters and returns all {@link City} objects with population density less than the given value.
     *
     * @param populationDensity the maximum population density
     * @return a collection of cities with population density less than the specified value
     */
    public Collection<City> filterLessThanPopulationDensity(long populationDensity) {
        return crudDao.getAll().stream()
                .filter(city -> city.getPopulationDensity() < populationDensity)
                .toList();
    }

    /**
     * Retrieves metadata about the current state of the database.
     *
     * @return a {@link DatabaseMetaData} object containing metadata
     */
    public DatabaseMetaData getMetaData() {
        return crudDao.getMetaData();
    }

    /**
     * Retrieves a list of {@link Government} types used in the city collection,
     * sorted in descending order.
     *
     * @return a list of government types in descending order
     */
    public List<Government> getGovernmentsDescending() {
        return crudDao.getAll().stream()
                .map(City::getGovernment)
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .toList();
    }
}
