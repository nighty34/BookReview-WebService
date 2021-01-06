package ch.bzz.it.buchbewertungen.data;

import ch.bzz.it.buchbewertungen.util.Result;

import java.util.List;

/**
 * short description
 * <p>
 * BookReview-WebService
 *
 * @author Felix Reiniger
 * @version 1.0
 * @since 06.01.21
 */
public interface Dao<T, K>{

    /**
     * gets all entities as a list
     * @param k key
     * @return a list of entities
     */
    default List<T> getAll(K k) {
        throw new UnsupportedOperationException();
    }

    /**
     * get a single entity
     * @param k the key, in SQL format (e.g. uuid="0ff34...")
     * @return the entity
     */
    default T getEntity(K k){
        throw new UnsupportedOperationException();
    }

    /**
     * save a single entity
     * @param t the entity to save
     * @return wheter saving was successful
     */
    default Result save(T t){
        throw new UnsupportedOperationException();
    }
}
