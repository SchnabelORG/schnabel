package com.schnabel.schnabel.misc.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface of a basic CRUD service
 * 
 * @param <T> Represents the type of object for which the service is used for
 * @param <I> Represents the type with which <T> is identified
 */

public interface IJpaService<T, I>
{
    Optional<T> add(T object);
    boolean remove(I id);
    boolean update(T object);
    Optional<T> get(I id);
    Iterable<T> getAll();
    Page<T> getAll(Pageable pageable);
}
