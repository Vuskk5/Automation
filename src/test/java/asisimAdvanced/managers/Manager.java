package asisimAdvanced.managers;

import java.util.List;

public interface Manager<T> {
    /**
     * Sends a request, sets and returns a list with every object of type {@code T}.
     * @return List of type {@code T}
     */
    List<T> requestAll();

    /**
     * Returns a list with every object of type {@code T} from memory.
     * @return List of type {@code T}
     */
    List<T> getAll();

    /**
     * Searches the list for an object with the given id.
     * @return The object with the given id, or null.
     */
    T getById(Long id);
}
