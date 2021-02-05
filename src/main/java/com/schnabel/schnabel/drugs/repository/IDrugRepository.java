package com.schnabel.schnabel.drugs.repository;

import com.schnabel.schnabel.drugs.model.Drug;

import org.springframework.data.repository.CrudRepository;

/**
 * Drug repository interface
 */
public interface IDrugRepository extends CrudRepository<Drug, Long>
{
        
}
