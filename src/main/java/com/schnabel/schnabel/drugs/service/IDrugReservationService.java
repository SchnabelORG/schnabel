package com.schnabel.schnabel.drugs.service;


import com.schnabel.schnabel.drugs.dto.DrugReservationDTO;
import com.schnabel.schnabel.drugs.model.DrugReservation;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.DrugReservationRequest;
import com.schnabel.schnabel.users.model.Patient;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;

/**
 * Drug reservation service interface
 */
public interface IDrugReservationService extends IJpaService<DrugReservation, Long> {
    Optional<DrugReservationDTO> getDTO(Long id);
    boolean reserveDrug(DrugReservationRequest req, Patient patient);
    PagedModel<DrugReservationDTO> findByPatientId(Long patientId, Pageable pageable);
    boolean cancelReservation(Long patientId, Long resId);
    /**
     * Returns missed reservations by patient
     */
    List<DrugReservation> findMissed(Long patientId);
}
