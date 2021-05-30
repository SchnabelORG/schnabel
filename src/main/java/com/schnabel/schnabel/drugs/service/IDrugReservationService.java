package com.schnabel.schnabel.drugs.service;


import com.schnabel.schnabel.drugs.dto.DrugReservationDTO;
import com.schnabel.schnabel.drugs.model.DrugReservation;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.model.Patient;

import java.util.Optional;

/**
 * Drug reservation service interface
 */
public interface IDrugReservationService extends IJpaService<DrugReservation, Long> {
    Optional<DrugReservationDTO> getDTO(Long id);

}
