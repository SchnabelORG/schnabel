package com.schnabel.schnabel.misc.implementations;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import org.springframework.data.repository.CrudRepository;

/**
 * Implementation of a basic CRUD service
 * 
 * @param <T> Represents the type of object for which the service is used for
 * @param <I> Represents the type with which <T> is identified
 */

public class CrudService<T extends IIdentifiable<I>, I> implements ICrudService<T, I>
{
    protected CrudRepository<T, I> repository;

    public CrudService(CrudRepository<T, I> repository)
    {
        this.repository = repository;
    }

	@Override
    public T add(T object)
    {
        
        if(object.getId() == null || !repository.existsById(object.getId()) )
        {
            repository.save(object);
        }
        return get(object.getId());
	}

	@Override
    public boolean remove(I id)
    {
        if(repository.existsById(id))
        {
            repository.delete(get(id));
            return true;
        }
		return false;
	}

	@Override
    public boolean update(T object)
    {
        if(repository.existsById(object.getId()))
        {
            repository.save(object);
            return true;
        }
		return false;
	}

	@Override
    public T get(I id)
    {
        return repository.findById(id).orElse(null);
	}

	@Override
    public Iterable<T> getAll() 
    {
        return repository.findAll();
	}

    
}
