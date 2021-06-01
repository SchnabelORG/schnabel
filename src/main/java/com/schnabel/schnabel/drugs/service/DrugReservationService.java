package com.schnabel.schnabel.drugs.service;


import com.schnabel.schnabel.drugs.dto.DrugReservationAssembler;
import com.schnabel.schnabel.drugs.dto.DrugReservationDTO;
import com.schnabel.schnabel.drugs.model.DrugReservation;
import com.schnabel.schnabel.drugs.repository.IDrugReservationRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import org.springframework.data.web.PagedResourcesAssembler;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Drug reservation service implementation
 */
@Service
public class DrugReservationService extends JpaService<DrugReservation, Long, IDrugReservationRepository> implements IDrugReservationService {


    private final DrugReservationAssembler drugReservationAssembler;
    private final PagedResourcesAssembler<DrugReservation> drugReservationPagedResourcesAssembler;

    public DrugReservationService(IDrugReservationRepository drugReservationRepository, DrugReservationAssembler drugReservationAssembler, PagedResourcesAssembler<DrugReservation> drugReservationPagedResourcesAssembler)
    {
        super(drugReservationRepository);
        this.drugReservationAssembler = drugReservationAssembler;
        this.drugReservationPagedResourcesAssembler = drugReservationPagedResourcesAssembler;
    }

    @Override
    @Transactional
    public Optional<DrugReservationDTO> getDTO(Long id) {
        return get(id).map(drugReservationAssembler::toModel);
    }
}
