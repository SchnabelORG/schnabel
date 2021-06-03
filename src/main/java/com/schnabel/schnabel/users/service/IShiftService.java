package com.schnabel.schnabel.users.service;

import java.util.List;
import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.ShiftDTO;
import com.schnabel.schnabel.users.dto.ShiftRequest;
import com.schnabel.schnabel.users.model.Shift;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * Shift service interface
 */
public interface IShiftService extends IJpaService<Shift, Long>
{
    Optional<ShiftDTO> getDTO(Long id);
    PagedModel<ShiftDTO> getAllDTO(Pageable pageable);
    List<Shift> getAllByMedicalEmployee(Long medicalEmployeeId);
    Optional<ShiftDTO> getShiftMedicalEmployeePharmacy(Long medicalEmployeeId, Long pharmacyId);
    boolean defineDermatologistShift(ShiftRequest shiftRequest, String email);
    boolean definePharmacistShift(ShiftRequest shiftRequest, String email);
}
