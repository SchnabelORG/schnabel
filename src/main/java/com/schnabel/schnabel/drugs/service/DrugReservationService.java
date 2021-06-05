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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Drug reservation service implementation
 */
@Service
public class DrugReservationService extends JpaService<DrugReservation, Long, IDrugReservationRepository> implements IDrugReservationService {

    private final DrugReservationAssembler dtoAsm;
    private final PagedResourcesAssembler<DrugReservation> pageAsm;
    private final IWareHouseItemService warehouseService;
    private final IDrugService drugService;
    private final IPharmacyService phService;

    @Autowired
    public DrugReservationService(IDrugReservationRepository drugReservationRepository, DrugReservationAssembler drugReservationAssembler, PagedResourcesAssembler<DrugReservation> drugReservationPagedResourcesAssembler, IWareHouseItemService warehouseService, IDrugService drugService, IPharmacyService phService)
    {
        super(drugReservationRepository);
        this.dtoAsm = drugReservationAssembler;
        this.warehouseService = warehouseService;
        this.drugService = drugService;
        this.phService = phService;
        this.pageAsm = drugReservationPagedResourcesAssembler;
    }

    @Override
    @Transactional
    public Optional<DrugReservationDTO> getDTO(Long id) {
        return get(id).map(dtoAsm::toModel);
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

    @Override
    @Transactional
    public PagedModel<DrugReservationDTO> findByPatientId(Long patientId, Pageable pageable) {

        Page<DrugReservation> reservations = repository.findByPatientId(patientId, pageable);
        return pageAsm.toModel(reservations, dtoAsm);
    }

    @Override
    @Transactional
    public boolean cancelReservation(Long patientId, Long resId) {
        Optional<DrugReservation> reservation = get(resId);
        if(!reservation.isPresent() || !reservation.get().getPatient().getId().equals(patientId)) {
            return false;
        }
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        if(reservation.get().getPeriod().getEndTime().compareTo(tomorrow) < 0) {
            return false;
        }
        return remove(reservation.get().getId());
    }

}
