package ru.ifmo.se.db.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Custom ID manager for generating and managing unique IDs.
 * <p>
 * This class maintains a list of used IDs and available IDs to ensure that
 * generated IDs are unique. It supports removing IDs (to mark them as available)
 * and generating new unique IDs.
 * </p>
 */
public class IdManager {
    private final List<Long> idList;
    private final List<Long> availableIds;

    public IdManager(List<Long> ids) {
        idList = new ArrayList<>(ids);
        Collections.sort(idList);
        availableIds = new ArrayList<>();
        updateAvailableIds();
    }

    /**
     * Removes the specified ID from the list of used IDs and adds it to the list of available IDs.
     *
     * @param id the ID to remove
     */
    public void removeId(long id) {
        if (idList.contains(id)) {
            idList.remove(id);
            availableIds.add(id);
            Collections.sort(availableIds);
        }
    }

    /**
     * Returns an available ID. If no available IDs exist, it generates the next
     * available ID based on the highest ID in the list.
     *
     * @return the next available ID
     */
    public long getAvailableId() {
        if (!availableIds.isEmpty()) {
            long id = availableIds.remove(0);
            idList.add(id);
            Collections.sort(idList);
            return id;
        } else {
            long newId = idList.isEmpty() ? 1 : idList.get(idList.size() - 1) + 1;
            idList.add(newId);
            return newId;
        }
    }

    /**
     * Clears both the list of used IDs and available IDs.
     */
    public void clear() {
        idList.clear();
        availableIds.clear();
    }

    private void updateAvailableIds() {
        availableIds.clear();
        long maxId = idList.isEmpty() ? 0 : idList.get(idList.size() - 1);
        for (long i = 1; i <= maxId; i++) { // Начинаем с 1, чтобы 0 не попадал в availableIds
            if (!idList.contains(i)) {
                availableIds.add(i);
            }
        }
    }
}
