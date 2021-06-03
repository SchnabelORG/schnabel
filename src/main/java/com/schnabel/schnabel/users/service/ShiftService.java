package com.schnabel.schnabel.users.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.dto.ShiftDTO;
import com.schnabel.schnabel.users.dto.ShiftDTOAssembler;
import com.schnabel.schnabel.users.dto.ShiftRequest;
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
    private final IPharmacyAdminService pharmacyAdminService;
    private final IPharmacistService pharmacistService;
    private final IDermatologistService dermatologistService;

    @Autowired
    public ShiftService(IShiftRepository repository, ShiftDTOAssembler shiftDTOAssembler, PagedResourcesAssembler<Shift> shiftPagedResourcesAssembler, IPharmacyAdminService pharmacyAdminService, IPharmacistService pharmacistService, IDermatologistService dermatologistService)
    {
        super(repository);
        this.shiftDTOAssembler = shiftDTOAssembler;
        this.shiftPagedResourcesAssembler = shiftPagedResourcesAssembler;
        this.pharmacyAdminService = pharmacyAdminService;
        this.pharmacistService = pharmacistService;
        this.dermatologistService = dermatologistService;
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
    public boolean defineDermatologistShift(ShiftRequest shiftRequest, String email) 
    {
        List<Shift> shifts = getAllByMedicalEmployee(shiftRequest.getMedicalEmployeeId());
        for (Shift s : shifts) {
            if(s.getStartTime().isBefore(shiftRequest.getEndTime()) && shiftRequest.getStartTime().isBefore(s.getEndTime())) {
                return false;
            }
        }
        Shift newShift = new Shift(shiftRequest.getStartTime(), shiftRequest.getEndTime(), dermatologistService.get(shiftRequest.getMedicalEmployeeId()).get(), pharmacyAdminService.findByEmail(email).get().getPharmacy());
        Optional<Shift> shift = add(newShift);
        return shift.isPresent();
    }

    @Override
    public boolean definePharmacistShift(ShiftRequest shiftRequest, String email) 
    {
        Shift newShift = new Shift(shiftRequest.getStartTime(), shiftRequest.getEndTime(), pharmacistService.get(shiftRequest.getMedicalEmployeeId()).get(), pharmacyAdminService.findByEmail(email).get().getPharmacy());
        Optional<Shift> shift = add(newShift);
        return shift.isPresent();
    }

}
