package com.schnabel.schnabel.pswregistration.repository;

import com.schnabel.schnabel.pswregistration.model.SpecialOffer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpecialOfferRepository extends CrudRepository<SpecialOffer, Integer> {
    
}
