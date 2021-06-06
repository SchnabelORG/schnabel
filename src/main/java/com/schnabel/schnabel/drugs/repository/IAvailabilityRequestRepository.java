package com.schnabel.schnabel.drugs.repository;


import com.schnabel.schnabel.drugs.model.AvailabilityRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Availability Request Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "availability_request", path = "avlreq")
public interface IAvailabilityRequestRepository extends JpaRepository<AvailabilityRequest, Long>
{
    Page<AvailabilityRequest> findByPharmacyId(Long id, Pageable pageable);
}
