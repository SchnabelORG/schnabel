package com.schnabel.schnabel.specialoffer.repository;

import com.schnabel.schnabel.specialoffer.model.SpecialOffer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpecialOfferRepository extends CrudRepository<SpecialOffer, Long>
{
    
}
