package com.schnabel.schnabel.drugs.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.Drug_;

import org.springframework.data.jpa.domain.Specification;

/**
 * Used for generating complex queries
 */
public class DrugSpecification {
    
    public static Specification<Drug> filteredQuery(Map<String, String> params) {
        return (root, query, cb) -> {
            return cb.and(getPredicates(params, root, cb).toArray(new Predicate[0])); 
        };
    }

    private static List<Predicate> getPredicates(Map<String, String> params, Root<Drug> root, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<Predicate>();
        if(params.containsKey("name")) {
            predicates.add(cb.like(cb.lower(root.get(Drug_.NAME)), "%" + params.get("name").toLowerCase() + "%"));
        }
        return predicates;
    }

}
