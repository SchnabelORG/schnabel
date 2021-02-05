package com.schnabel.schnabel.pharmacies.repository;

import com.schnabel.schnabel.pharmacies.model.Term;
import org.springframework.data.repository.CrudRepository;

/**
 * Term CRUD repository interface
 */
public interface ITermRepository extends CrudRepository<Term, Long> {
}
