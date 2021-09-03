package com.schnabel.schnabel.appointment.repository;

import com.schnabel.schnabel.appointment.model.AppointmentReport;
import com.schnabel.schnabel.appointment.model.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "loyalty", path = "loyalty")
public interface ILoyaltyRepository extends JpaRepository<LoyaltyProgram, Long> {
}
