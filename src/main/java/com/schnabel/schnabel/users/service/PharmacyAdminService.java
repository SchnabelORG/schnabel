package com.schnabel.schnabel.users.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.repository.IAppointmentRepository;
import com.schnabel.schnabel.appointment.service.IAppointmentService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.model.Pharmacist;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.repository.IPharmacyAdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class PharmacyAdminService extends JpaService<PharmacyAdmin, Long, IPharmacyAdminRepository> implements IPharmacyAdminService
{
    private final IDermatologistService dermatologistService;
    private final IPharmacistService pharmacistService;
    private final IWareHouseItemService wareHouseItemService;
    private final IAppointmentRepository appointmentRepository;

    @Autowired
    public PharmacyAdminService(IPharmacyAdminRepository pharmacyAdminRepository, IDermatologistService dermatologistService, IPharmacistService pharmacistService, IWareHouseItemService wareHouseItemService, IAppointmentRepository appointmentRepository)
    {
        super(pharmacyAdminRepository);
        this.dermatologistService = dermatologistService;
        this.pharmacistService = pharmacistService;
        this.wareHouseItemService = wareHouseItemService;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Optional<PharmacyAdmin> findByEmail(String email)
    {
        return repository.findByEmail(email);
    }

    @Override
    public PagedModel<DermatologistDTO> getAllDermatologists(String email, Pageable pageable)
    {
        return dermatologistService.findAllByPharmacy(findByEmail(email).get().getPharmacy().getId(), pageable);
    }

    @Override
    public PagedModel<PharmacistDTO> getAllPharmacists(String email, Pageable pageable)
    {
        return pharmacistService.findByPharmacy(findByEmail(email).get().getPharmacy().getId(), pageable);
    }

    @Override
    public PagedModel<WareHouseItemDTO> getAllDrugs(String email, Pageable pageable)
    {
        return wareHouseItemService.findAllByPharmacyId(findByEmail(email).get().getPharmacy().getId(), pageable);
    }

    @Override
    public boolean removePharmacist(Long id)
    {
        List<Appointment> appointments = appointmentRepository.findByMedicalEmployeeId(id);
        for (Appointment appointment : appointments) {
            if(appointment.getPeriod().getStartTime().isAfter(LocalDateTime.now()) && !appointment.isFree()) {
                return false;
            }
        }
        Pharmacist pharmacist = pharmacistService.get(id).get();
        pharmacist.setPharmacy(null);
        return pharmacistService.update(pharmacist);
    }

    @Override
    public boolean removeDermatologist(Long id, String email)
    {
        List<Appointment> appointments = appointmentRepository.findByMedicalEmployeeId(id);
        for (Appointment appointment : appointments) {
            if(appointment.getPeriod().getStartTime().isAfter(LocalDateTime.now()) && !appointment.isFree()) {
                return false;
            }
        }
        //Dermatologist dermatologist = dermatologistService.get(id).get();
        /*List<Pharmacy> pharmacies = dermatologist.getPharmacies();
        for (Pharmacy pharmacy : pharmacies) {
            System.out.println("pharmacy " + pharmacy.getId());
        }*/
        return true;
    }

    /*@Override
    public PagedModel<DermatologistDTO> getDermatologistToAdd(Long pharmacyId, Pageable pageable) 
    {
        Page<Dermatologist> dermatologists = repository.getDermatologistsNotPharmacy(pharmacyId, pageable);
        return offerPagedResourcesAssembler.toModel(offers, offerDTOAssembler);
        getDermatologistsNotPharmacy(pharmacyId, pageable);
    }*/

    @Override
    public List<Dermatologist> getDermatologistNotPharmacy(Long pharmacyId, Pageable pageable)
    {
        return repository.getDermatologistsNotPharmacy(pharmacyId, pageable);
    }


}