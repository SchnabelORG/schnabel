package com.schnabel.schnabel.terms.repository;

import com.schnabel.schnabel.terms.model.Term;

import org.springframework.data.repository.CrudRepository;

/**
 * Term CRUD repository interface
 */
public interface ITermRepository extends CrudRepository<Term, Long> {
}
