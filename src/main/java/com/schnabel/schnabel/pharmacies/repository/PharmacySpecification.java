package com.schnabel.schnabel.pharmacies.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.schnabel.schnabel.misc.model.Address_;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.model.Pharmacy_;

import org.springframework.data.jpa.domain.Specification;

/**
 * Used for generating complex queries
 */
public class PharmacySpecification {

    public static Specification<Pharmacy> filteredQuery(Map<String, String> params) {

        return (root, query, cb) -> {
            return cb.and(getPredicates(params, root, cb).toArray(Predicate[]::new)); 
        };
    }

    public static List<Predicate> getPredicates(Map<String, String> params, Root<Pharmacy> root, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<Predicate>();
        // TODO(Jovan): Ugly, probably better way using reflections?
        if(params.containsKey("name")) {
            predicates.add(cb.like(cb.lower(root.get(Pharmacy_.NAME)), "%" + params.get("name").toLowerCase() + "%"));
        }

        if(params.containsKey("street")) {
            predicates.add(cb.like(cb.lower(root.get(Pharmacy_.ADDRESS).get(Address_.STREET)), "%" + params.get("street").toLowerCase() + "%"));
        }
        
        if(params.containsKey("street_no")) {
            predicates.add(cb.like(cb.lower(root.get(Pharmacy_.ADDRESS).get(Address_.STREET_NO)), "%" + params.get("street_no").toLowerCase() + "%"));
        }
        
        if(params.containsKey("city")) {
            predicates.add(cb.like(cb.lower(root.get(Pharmacy_.ADDRESS).get(Address_.CITY)), "%" + params.get("city").toLowerCase() + "%"));
        }

        if(params.containsKey("score")) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Pharmacy_.SCORE), Double.parseDouble(params.get("score"))));
        }

        return predicates;
    }

}
