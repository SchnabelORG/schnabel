package com.schnabel.schnabel.misc.interfaces;

public interface ICrudService<T, ID>
{
    boolean add(T object);
    boolean remove(ID id);
    boolean update(T object);
    T get(ID id);
    Iterable<T> getAll();
}
