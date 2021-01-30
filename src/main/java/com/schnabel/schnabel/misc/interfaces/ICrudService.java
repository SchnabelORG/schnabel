package com.schnabel.schnabel.misc.interfaces;

/**
 * Interface of a basic CRUD service
 * 
 * @param <T> Represents the type of object for which the service is used for
 * @param <I> Represents the type with which <T> is identified
 */

public interface ICrudService<T, I>
{
    boolean add(T object);
    boolean remove(I id);
    boolean update(T object);
    T get(I id);
    Iterable<T> getAll();
}
