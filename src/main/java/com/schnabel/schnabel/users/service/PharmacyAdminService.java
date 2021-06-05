package com.schnabel.schnabel.users.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.repository.IAppointmentRepository;
import com.schnabel.schnabel.appointment.service.IAppointmentService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.dto.PharmacistRequest;
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
    private final IShiftService shiftService;

    @Autowired
    public PharmacyAdminService(IPharmacyAdminRepository pharmacyAdminRepository, IDermatologistService dermatologistService, IPharmacistService pharmacistService, IWareHouseItemService wareHouseItemService, IAppointmentRepository appointmentRepository, IShiftService shiftService)
    {
        super(pharmacyAdminRepository);
        this.dermatologistService = dermatologistService;
        this.pharmacistService = pharmacistService;
        this.wareHouseItemService = wareHouseItemService;
        this.appointmentRepository = appointmentRepository;
        this.shiftService = shiftService;
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
        return true;
    }

    @Override
    public List<Dermatologist> getDermatologistNotPharmacy(Long pharmacyId, Pageable pageable)
    {
        return repository.getDermatologistsNotPharmacy(pharmacyId, pageable);
    }

    @Override
    public boolean addPharmacist(PharmacistRequest pharmacistRequest, String email) 
    {
        Pharmacist newPharmacist = new Pharmacist(pharmacistRequest.getName(), pharmacistRequest.getSurname(), pharmacistRequest.getEmail(), pharmacistRequest.getPassword(), new Address(pharmacistRequest.getPostcode(), pharmacistRequest.getCity(), pharmacistRequest.getStreet(), pharmacistRequest.getStreetNo()), findByEmail(email).get().getPharmacy());
        System.out.println("konstruktor");
        Optional<Pharmacist> pharmacist = pharmacistService.add(newPharmacist);
        System.out.println("usao");
        if(!pharmacist.isPresent()) {
            System.out.println("usao1");
            return false;
        }
        return shiftService.definePharmacistShift(pharmacistRequest.getStartTime(), pharmacistRequest.getEndTime(), pharmacist.get(), findByEmail(email).get().getPharmacy());
    }

    @Override
    public PagedModel<DermatologistDTO> filteredSearchPharmacyAdmin(Map<String, String> params, String email, Pageable pageable)
    {
        return dermatologistService.filteredSearchPharmacyAdmin(params, findByEmail(email).get().getPharmacy().getId(), pageable);
    }


}