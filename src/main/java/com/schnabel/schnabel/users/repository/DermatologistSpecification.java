package com.schnabel.schnabel.users.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.model.Dermatologist_;

import org.springframework.data.jpa.domain.Specification;

/**
 * Used for generating complex queries
 */
public class DermatologistSpecification {

    public static Specification<Dermatologist> filteredQuery(Map<String, String> params) {

        return (root, query, cb) -> {
            return cb.and(getPredicates(params, root, cb).toArray(new Predicate[0])); 
        };
    }

    public static List<Predicate> getPredicates(Map<String, String> params, Root<Dermatologist> root, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<Predicate>();

        if(params.containsKey("name")) {
            predicates.add(cb.like(cb.lower(root.get(Dermatologist_.NAME)), "%" + params.get("name").toLowerCase() + "%"));
        }

        if(params.containsKey("surname")) {
            predicates.add(cb.like(cb.lower(root.get(Dermatologist_.SURNAME)), "%" + params.get("surname").toLowerCase() + "%"));
        }

        if(params.containsKey("score")) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Dermatologist_.SCORE), params.get("score")));
        }

        return predicates;
    }


}
