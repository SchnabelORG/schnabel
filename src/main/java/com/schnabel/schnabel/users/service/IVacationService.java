package com.schnabel.schnabel.users.service;

import java.util.List;
import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.VacationDTO;
import com.schnabel.schnabel.users.dto.VacationRequest;
import com.schnabel.schnabel.users.model.Vacation;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * Vacation service interface
 */
public interface IVacationService extends IJpaService<Vacation, Long> 
{
    Optional<VacationDTO> getDTO(Long id);
    PagedModel<VacationDTO> getAllDTO(Pageable pageable);
    PagedModel<VacationDTO> getCreatedVacationsByPharmacy(String email, Pageable pageable);
    List<Vacation> findByMedicalEmployeeId(Long id);
    boolean acceptVacation(Long id);
    boolean rejectVacation(VacationRequest vacationRequest);
}
