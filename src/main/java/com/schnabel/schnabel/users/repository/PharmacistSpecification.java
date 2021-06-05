package com.schnabel.schnabel.users.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.schnabel.schnabel.pharmacies.model.Pharmacy_;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.model.Pharmacist_;

import org.springframework.data.jpa.domain.Specification;

/**
 * Used for generating complex queries
 */
public class PharmacistSpecification {

    public static Specification<Pharmacist> filteredQuery(Map<String, String> params) {

        return (root, query, cb) -> {
            return cb.and(getPredicates(params, root, cb).toArray(new Predicate[0])); 
        };
    }

    public static List<Predicate> getPredicates(Map<String, String> params, Root<Pharmacist> root, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<Predicate>();

        if(params.containsKey("name")) {
            predicates.add(cb.like(cb.lower(root.get(Pharmacist_.NAME)), "%" + params.get("name").toLowerCase() + "%"));
        }

        if(params.containsKey("surname")) {
            predicates.add(cb.like(cb.lower(root.get(Pharmacist_.SURNAME)), "%" + params.get("surname").toLowerCase() + "%"));
        }

        if(params.containsKey("score")) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Pharmacist_.SCORE), params.get("score")));
        }

        if(params.containsKey("pharmacy")) {
            predicates.add(cb.equal(root.get(Pharmacist_.PHARMACY).get(Pharmacy_.ID), "%" + params.get("pharmacy_id") + "%"));
        }

        return predicates;
    }


}
