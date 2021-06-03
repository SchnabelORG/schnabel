package com.schnabel.schnabel.drugs.service;


import com.schnabel.schnabel.drugs.dto.DrugReservationAssembler;
import com.schnabel.schnabel.drugs.dto.DrugReservationDTO;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.DrugReservation;
import com.schnabel.schnabel.drugs.repository.IDrugReservationRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;
import com.schnabel.schnabel.users.dto.DrugReservationRequest;
import com.schnabel.schnabel.users.model.Patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Drug reservation service implementation
 */
@Service
public class DrugReservationService extends JpaService<DrugReservation, Long, IDrugReservationRepository> implements IDrugReservationService {

    private final DrugReservationAssembler drugReservationAssembler;
    private final IWareHouseItemService warehouseService;
    private final IDrugService drugService;
    private final IPharmacyService phService;

    @Autowired
    public DrugReservationService(IDrugReservationRepository drugReservationRepository, DrugReservationAssembler drugReservationAssembler, IWareHouseItemService warehouseService, IDrugService drugService, IPharmacyService phService)
    {
        super(drugReservationRepository);
        this.drugReservationAssembler = drugReservationAssembler;
        this.warehouseService = warehouseService;
        this.drugService = drugService;
        this.phService = phService;
    }

    @Override
    @Transactional
    public Optional<DrugReservationDTO> getDTO(Long id) {
        return get(id).map(drugReservationAssembler::toModel);
    }

    @Override
    public boolean reserveDrug(DrugReservationRequest req, Patient patient) {
        Optional<Drug> drug = drugService.get(req.getDrugId());
        Optional<Pharmacy> pharmacy = phService.get(req.getPharmacyId());
        if(!drug.isPresent() || !pharmacy.isPresent()) {
            return false;
        }
        DrugReservation res = new DrugReservation(req.getQuantity(), req.getPeriod(), patient, drug.get(), pharmacy.get());
        return warehouseService.reserveDrug(req, patient)
            && add(res).isPresent();
    }
}
