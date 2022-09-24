package com.edu.ulab.app.repository;

import java.util.Optional;

public interface EntityRepository <K extends Number,V >  {
    /**
     * Saves a given entity. Use the returned instance
     * for further operations as the save operation might have changed the entity instance completely.
     * @param entity must not be null.
     * @return the saved entity; will never be null.
     */
    V save(V entity);

    /**
     * Retrieves an entity by its id.
     * @param primaryKey id - must not be null.
     * @return the entity with the given id or Optional#empty() if none found
     */
    Optional<V> findById(K primaryKey);

    /**
     * Deletes a given entity.
     * @param entity entity - must not be null.
     */
    void delete(V entity);

    /**
     * Iterable<T> findAll()
     * Returns all instances of the type.
     * @return all entities
     */
    Iterable<V> findAll();
}
