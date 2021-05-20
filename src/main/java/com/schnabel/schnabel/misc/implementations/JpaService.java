package com.schnabel.schnabel.misc.implementations;

import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.misc.model.IIdentifiable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Implementation of a basic CRUD service
 * 
 * @param <T> Represents the type of object for which the service is used for
 * @param <I> Represents the type with which <T> is identified
 */

public class JpaService<T extends IIdentifiable<I>, I, R extends JpaRepository<T, I>> implements IJpaService<T, I>
{
    protected R repository;

    public JpaService(R repository)
    {
        this.repository = repository;
    }

	@Override
    public Optional<T> add(T object)
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
        Optional<T> entity = repository.findById(id);
        if(entity.isPresent())
        {
            repository.delete(entity.get());
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
    public Optional<T> get(I id)
    {
        return repository.findById(id);
	}

	@Override
    public Iterable<T> getAll() 
    {
        return repository.findAll();
	}

    @Override
    public Page<T> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
