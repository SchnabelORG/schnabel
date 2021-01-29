package com.schnabel.schnabel.misc.implementations;

import com.schnabel.schnabel.misc.interfaces.ICrudService;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import org.springframework.data.repository.CrudRepository;

public class CrudService<T extends IIdentifiable<ID>, ID> implements ICrudService<T, ID>
{

    protected CrudRepository<T, ID> repository;

    public CrudService(CrudRepository<T, ID> repository)
    {
        this.repository = repository;
    }

	@Override
    public boolean add(T object)
    {
        if(!repository.existsById(object.getId()))
        {
            repository.save(object);
            return true;
        }
		return false;
	}

	@Override
    public boolean remove(ID id)
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
    public T get(ID id)
    {
        return repository.findById(id).orElse(null);
	}

	@Override
    public Iterable<T> getAll() 
    {
        return repository.findAll();
	}

    
}
