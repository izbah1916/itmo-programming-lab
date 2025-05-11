package ru.ifmo.se.service.receiver;

import lombok.RequiredArgsConstructor;
import ru.ifmo.se.db.dao.CrudDao;
import ru.ifmo.se.db.data.DatabaseDump;
import ru.ifmo.se.model.City;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling modification operations on {@link City} objects.
 * Acts as an intermediary between commands and the data access layer.
 */
@RequiredArgsConstructor
public class ModificationReceiver {

    /**
     * Data access object for performing CRUD operations on {@link City}.
     */
    private final CrudDao<City> crudDao;

    /**
     * Adds a new {@link City} to the data source.
     *
     * @param city the city to be added
     */
    public void add(City city) {
        crudDao.create(city);
    }

    /**
     * Updates an existing {@link City} identified by its ID.
     *
     * @param id the ID of the city to update
     * @param updatedCity the city object with updated fields
     * @return {@code true} if the city was found and updated, {@code false} otherwise
     */
    public boolean update(long id, City updatedCity) {
        Optional<City> maybeCity = crudDao.findById(id);
        if (maybeCity.isPresent()) {
            updatedCity.setId(id);
            updatedCity.setCreationDate(maybeCity.get().getCreationDate());
        } else {
            return false;
        }
        return crudDao.update(updatedCity);
    }

    /**
     * Finds a {@link City} by its ID.
     *
     * @param id the ID of the city
     * @return an {@link Optional} containing the city if found, or empty otherwise
     */
    public Optional<City> findById(long id) {
        return crudDao.findById(id);
    }

    /**
     * Removes a {@link City} by its ID.
     *
     * @param id the ID of the city to remove
     * @return {@code true} if the city was successfully removed, {@code false} otherwise
     */
    public boolean removeById(long id) {
        return crudDao.removeById(id);
    }

    /**
     * Removes the first {@link City} in the collection.
     *
     * @return {@code true} if a city was removed, {@code false} if the collection was empty
     */
    public boolean removeFirst() {
        return crudDao.removeById(crudDao.getAll().iterator().next().getId());
    }

    /**
     * Clears all {@link City} entries from the collection.
     */
    public void clear() {
        crudDao.clear();
    }

    /**
     * Retrieves a database dump containing all {@link City} entries.
     *
     * @return a {@link DatabaseDump} of cities
     */
    public DatabaseDump<City> getDump() {
        return crudDao.getDump();
    }

    /**
     * Removes all cities with the specified population density.
     *
     * @param populationDensity the population density to match
     * @return the number of cities removed
     */
    public long removeAllByPopulationDensity(long populationDensity) {
        List<City> toBeRemovedList = crudDao.getAll().stream()
                .filter(city -> city.getPopulationDensity() == populationDensity)
                .toList();

        toBeRemovedList.forEach(city -> crudDao.removeById(city.getId()));
        return toBeRemovedList.size();
    }

    /**
     * Checks whether the specified {@link City} is greater than the current maximum city.
     * Uses {@link City#compareTo(City)} for comparison.
     *
     * @param city the city to compare
     * @return {@code true} if the city is greater than all others, {@code false} otherwise
     */
    public boolean isMax(City city) {
        return crudDao.getAll().stream()
                .max(City::compareTo)
                .map(maxCity -> city.compareTo(maxCity) > 0)
                .orElse(true);
    }
}
