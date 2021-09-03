package com.schnabel.schnabel.grades.repository;

import com.schnabel.schnabel.grades.model.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Complaint Jpa repository interface
 */
@RepositoryRestResource(collectionResourceRel = "complaints", path = "complaint")
public interface IComplaintRepository extends JpaRepository<Complaint, Long>
{
    Page<Complaint> findByResponseNull(Pageable pageable);
}
