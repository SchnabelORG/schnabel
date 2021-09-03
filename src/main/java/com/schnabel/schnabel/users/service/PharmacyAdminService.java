package com.schnabel.schnabel.users.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import javax.transaction.Transactional;

import com.schnabel.schnabel.appointment.model.Appointment;
import com.schnabel.schnabel.appointment.repository.IAppointmentRepository;
import com.schnabel.schnabel.drugs.model.DrugReservation;
import com.schnabel.schnabel.drugs.repository.IDrugReservationRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.misc.model.Address;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTO;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTOAssembler;
import com.schnabel.schnabel.users.model.*;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.DermatologistRequest;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.dto.PharmacistRequest;
import com.schnabel.schnabel.users.repository.IPharmacyAdminRepository;

import com.schnabel.schnabel.users.repository.IRoleRepository;
import com.schnabel.schnabel.users.repository.IUserssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PharmacyAdminService extends JpaService<PharmacyAdmin, Long, IPharmacyAdminRepository> implements IPharmacyAdminService
{
    private final PharmacyAdminDTOAssembler pharmacyAdminDTOAssembler;
    private final PagedResourcesAssembler<PharmacyAdmin> pharmacyAdminDtoPagedResourcesAssembler;
    private final IPharmacyRepository pharmacyRepository;
    private final PasswordEncoder passwordEncoder;
    private final IDermatologistService dermatologistService;
    private final IPharmacistService pharmacistService;
    private final IWareHouseItemService wareHouseItemService;
    private final IAppointmentRepository appointmentRepository;
    private final IDrugReservationRepository drugReservationRepository;
    private final IShiftService shiftService;
    private final IRoleRepository roleRepository;
    private final IUserssRepository userssRepository;

    @Autowired
    public PharmacyAdminService(IPharmacyAdminRepository pharmacyAdminRepository, PharmacyAdminDTOAssembler pharmacyAdminDTOAssembler, PagedResourcesAssembler<PharmacyAdmin> pharmacyAdminDtoPagedResourcesAssembler, IPharmacyRepository pharmacyRepository, IDermatologistService dermatologistService, IPharmacistService pharmacistService, IWareHouseItemService wareHouseItemService, IAppointmentRepository appointmentRepository, IShiftService shiftService, IDrugReservationRepository drugReservationRepository, IRoleRepository roleRepository, IUserssRepository userssRepository)
    {
        super(pharmacyAdminRepository);
        this.pharmacyAdminDTOAssembler = pharmacyAdminDTOAssembler;
        this.pharmacyAdminDtoPagedResourcesAssembler = pharmacyAdminDtoPagedResourcesAssembler;
        this.pharmacyRepository = pharmacyRepository;
        this.roleRepository = roleRepository;
        this.userssRepository = userssRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.dermatologistService = dermatologistService;
        this.pharmacistService = pharmacistService;
        this.wareHouseItemService = wareHouseItemService;
        this.appointmentRepository = appointmentRepository;
        this.shiftService = shiftService;
        this.drugReservationRepository = drugReservationRepository;
    }


    @Override
    public Optional<PharmacyAdminDTO> getDTO(Long id) {
        return get(id).map(pharmacyAdminDTOAssembler::toModel);
    }

    @Override
    public PagedModel<PharmacyAdminDTO> getAllDTO(Pageable pageable) {
        Page<PharmacyAdmin> pharmacyAdmins = getAll(pageable);
        return pharmacyAdminDtoPagedResourcesAssembler.toModel(pharmacyAdmins, pharmacyAdminDTOAssembler);
    }

    @Override
    public PagedModel<PharmacyAdminDTO> findByPharmacy(Pageable pageable, Long pharmacyId){
        Page<PharmacyAdmin> pharmacyAdmins =  repository.findByPharmacyId(pageable ,pharmacyId);
        return pharmacyAdminDtoPagedResourcesAssembler.toModel(pharmacyAdmins, pharmacyAdminDTOAssembler);
    }

    @Override
    public PagedModel<PharmacyAdminDTO> findWithoutPharmacy(Pageable pageable){
        Page<PharmacyAdmin> pharmacyAdmins = repository.findByPharmacyId(pageable, null);
        return pharmacyAdminDtoPagedResourcesAssembler.toModel(pharmacyAdmins, pharmacyAdminDTOAssembler);
    }

    @Override
    public boolean assignPharmacyAdmin(Long pharmacyAdminId, Long pharmacyId) {
        try {
            PharmacyAdmin padmin = repository.findById(pharmacyAdminId).get();
            Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).get();
            if (padmin != null && pharmacy != null) {
                padmin.setPharmacy(pharmacy);
                return update(padmin);
            }
            return false;
        } catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean registerPharmacyAdmin(String name, String surname, String email, String password, Address address) {
        String encodedPassword = passwordEncoder.encode(password);
        PharmacyAdmin newPharmacyAdmin = new PharmacyAdmin(name, surname, email, encodedPassword, address, false);
        newPharmacyAdmin.setActivated(false);
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(ERole.ROLE_PHARMACYADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(role);
        UserS user = new UserS(email, encodedPassword);
        user.setRoles(roles);
        userssRepository.save(user);
        Optional<PharmacyAdmin> pharmacyAdmin = add(newPharmacyAdmin);
        if(pharmacyAdmin.isPresent())
            return true;
        return false;
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
    @Transactional
    public boolean removePharmacist(Long id)
    {
        List<Appointment> appointments = appointmentRepository.findByMedicalEmployeeId(id);
        for (Appointment appointment : appointments) {
            if(appointment.getPeriod().getStartTime().isAfter(LocalDateTime.now()) && !appointment.isFree()) {
                return false;
            }
        }
        Optional<Shift> shift = shiftService.getShiftByMedicalEmployeePharmacy(id, pharmacistService.get(id).get().getPharmacy().getId());
        if(shift.isPresent()){
            shiftService.remove(shift.get().getId());
        }

        Pharmacist pharmacist = pharmacistService.get(id).get();
        pharmacist.setPharmacy(null);
        return pharmacistService.update(pharmacist);
    }

    @Override
    @Transactional
    public boolean removeDermatologist(Long id, String email)
    {
        List<Appointment> appointments = appointmentRepository.findByMedicalEmployeeId(id);
        for (Appointment appointment : appointments) {
            if(appointment.getPeriod().getStartTime().isAfter(LocalDateTime.now()) && !appointment.isFree()) {
                return false;
            }
        }
        Optional<Shift> shift = shiftService.getShiftByMedicalEmployeePharmacy(id, findByEmail(email).get().getPharmacy().getId());
        if(shift.isPresent()){
            shiftService.remove(shift.get().getId());
        }

        Dermatologist dermatologist = dermatologistService.get(id).get();
        dermatologist.getPharmacies().remove(findByEmail(email).get().getPharmacy());
        return dermatologistService.update(dermatologist);
    }

    @Override
    @Transactional
    public boolean addDermatologist(DermatologistRequest dermatologistRequest, String email)
    {
        List<Shift> shifts = shiftService.getAllByMedicalEmployee(dermatologistRequest.getId());
        for (Shift s : shifts) {
            if(s.getStartTime().isBefore(dermatologistRequest.getEndTime()) && dermatologistRequest.getStartTime().isBefore(s.getEndTime())) {
                return false;
            }
        }

        Dermatologist dermatologist = dermatologistService.get(dermatologistRequest.getId()).get();
        dermatologist.getPharmacies().add(findByEmail(email).get().getPharmacy());
        if(!dermatologistService.update(dermatologist)) {
            return false;
        }

        Shift newShift = new Shift(dermatologistRequest.getStartTime(), dermatologistRequest.getEndTime(), dermatologistService.get(dermatologistRequest.getId()).get(), findByEmail(email).get().getPharmacy());
        Optional<Shift> shift = shiftService.add(newShift);
        if(!shift.isPresent()){
            return false;
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
        Optional<Pharmacist> pharmacist = pharmacistService.add(newPharmacist);
        if(!pharmacist.isPresent()) {
            return false;
        }
        return shiftService.definePharmacistShift(pharmacistRequest.getStartTime(), pharmacistRequest.getEndTime(), pharmacist.get(), findByEmail(email).get().getPharmacy());
    }

    @Override
    public PagedModel<DermatologistDTO> filteredSearchPharmacyAdmin(Map<String, String> params, String email, Pageable pageable)
    {
        return dermatologistService.filteredSearchPharmacyAdmin(params, findByEmail(email).get().getPharmacy().getId(), pageable);
    }

    @Override
    @Transactional
    public List<Integer> countDrugsByMonth(Long pharmacyId)
    {
        List<Integer> countMonth = new ArrayList<Integer>();
        List<DrugReservation> drugReservations = drugReservationRepository.findByPharmacyId(pharmacyId);
        System.out.println(drugReservations.size() + "-----------------------");

        for (int i = 1; i < 13; i++) {
            countMonth.add(count((i), drugReservations));
        }
        return countMonth;
    }

    private Integer count(Integer month, List<DrugReservation> drugReservations)
    {
        int counter = 0;
        for (DrugReservation drugReservation : drugReservations) {
            if(drugReservation.getPeriod().getEndTime().isBefore(LocalDateTime.now()) && drugReservation.getPeriod().getStartTime().getYear() == LocalDate.now().getYear() && drugReservation.getPeriod().getStartTime().getMonthValue() == month && drugReservation.isTaken())
            {
                counter += drugReservation.getQuantity();
            }
        }
        return counter;
    }

    @Override
    @Transactional
    public List<Integer> countDrugsByYear(Long pharmacyId)
    {
        List<Integer> countYear = new ArrayList<Integer>();
        List<DrugReservation> drugReservations = drugReservationRepository.findByPharmacyId(pharmacyId);

        for (int i = 0; i < 2; i++) {
            countYear.add(countYears((i), drugReservations));
        }
        return countYear;
    }

    private Integer countYears(Integer year, List<DrugReservation> drugReservations)
    {
        int counter = 0;
        for (DrugReservation drugReservation : drugReservations) {
            if(year == 0){
                if(drugReservation.isTaken() && drugReservation.getPeriod().getEndTime().isBefore(LocalDateTime.now()) && drugReservation.getPeriod().getStartTime().getYear() == LocalDate.now().getYear() - 1)
                {
                    counter += drugReservation.getQuantity();
                }
            } else {
                if(drugReservation.isTaken() && drugReservation.getPeriod().getEndTime().isBefore(LocalDateTime.now()) &&  drugReservation.getPeriod().getStartTime().getYear() == LocalDate.now().getYear())
                {
                    counter += drugReservation.getQuantity();
                }
            }
        }
        return counter;
    }

}
