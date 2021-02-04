package com.schnabel.schnabel.pharmacies.repository;

import com.schnabel.schnabel.pharmacies.model.Term;
import org.springframework.data.repository.CrudRepository;

public interface ITermRepository extends CrudRepository<Term, Integer> {
}
