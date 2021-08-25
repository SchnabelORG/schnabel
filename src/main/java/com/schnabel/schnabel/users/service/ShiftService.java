package com.schnabel.schnabel.users.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.users.dto.ShiftDTO;
import com.schnabel.schnabel.users.dto.ShiftDTOAssembler;
import com.schnabel.schnabel.users.dto.ShiftRequest;
import com.schnabel.schnabel.users.model.MedicalEmployee;
import com.schnabel.schnabel.users.model.Shift;
import com.schnabel.schnabel.users.repository.IShiftRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

/**
 * Shift service implementation
 */
@Service
public class ShiftService extends JpaService<Shift, Long, IShiftRepository> implements IShiftService
{
    private final ShiftDTOAssembler shiftDTOAssembler;
    private final PagedResourcesAssembler<Shift> shiftPagedResourcesAssembler;

    @Autowired
    public ShiftService(IShiftRepository repository, ShiftDTOAssembler shiftDTOAssembler, PagedResourcesAssembler<Shift> shiftPagedResourcesAssembler)
    {
        super(repository);
        this.shiftDTOAssembler = shiftDTOAssembler;
        this.shiftPagedResourcesAssembler = shiftPagedResourcesAssembler;
    }

    @Override
    @Transactional
    public Optional<ShiftDTO> getDTO(Long id) {
        return get(id).map(shiftDTOAssembler::toModel);
    }

    @Override
    @Transactional
    public PagedModel<ShiftDTO> getAllDTO(Pageable pageable) {

        Page<Shift> shifts = getAll(pageable);
        return shiftPagedResourcesAssembler.toModel(shifts, shiftDTOAssembler);
    }

    @Override
    public List<Shift> getAllByMedicalEmployee(Long medicalEmployeeId) 
    {
        return repository.findByMedicalEmployeeId(medicalEmployeeId);
    }

    @Override
    @Transactional
    public Optional<ShiftDTO> getShiftMedicalEmployeePharmacy(Long medicalEmployeeId, Long pharmacyId)
    {
        Optional<Shift> shift = repository.findShiftMedicalEmployeePharmacy(medicalEmployeeId, pharmacyId);
        return shift.map(shiftDTOAssembler::toModel);
    }

    @Override
    @Transactional
    public Optional<Shift> getShiftByMedicalEmployeePharmacy(Long medicalEmployeeId, Long pharmacyId)
    {
        return repository.findShiftMedicalEmployeePharmacy(medicalEmployeeId, pharmacyId);
    }

    @Override
    public boolean defineDermatologistShift(LocalTime startTime, LocalTime endTime, MedicalEmployee medicalEmployee, Pharmacy pharmacy) 
    {
        List<Shift> shifts = getAllByMedicalEmployee(medicalEmployee.getId());
        for (Shift s : shifts) {
            if(s.getStartTime().isBefore(endTime) && startTime.isBefore(s.getEndTime())) {
                return false;
            }
        }
        Shift newShift = new Shift(startTime, endTime, medicalEmployee, pharmacy);
        Optional<Shift> shift = add(newShift);
        return shift.isPresent();
    }

    @Override
    public boolean definePharmacistShift(LocalTime startTime, LocalTime endTime, MedicalEmployee medicalEmployee, Pharmacy pharmacy) 
    {
        Shift newShift = new Shift(startTime, endTime, medicalEmployee, pharmacy);
        Optional<Shift> shift = add(newShift);
        return shift.isPresent();
    }

}
