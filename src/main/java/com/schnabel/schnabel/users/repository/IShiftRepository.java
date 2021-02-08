package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.Shift;

import org.springframework.data.repository.CrudRepository;

public interface IShiftRepository extends CrudRepository<Shift, Long>
{
    
}
