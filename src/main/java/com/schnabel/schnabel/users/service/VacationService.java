package com.schnabel.schnabel.users.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.email.service.IMailService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.users.dto.VacationDTO;
import com.schnabel.schnabel.users.dto.VacationDTOAssembler;
import com.schnabel.schnabel.users.dto.VacationRequest;
import com.schnabel.schnabel.users.model.Vacation;
import com.schnabel.schnabel.users.model.VacationStatus;
import com.schnabel.schnabel.users.repository.IVacationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

/**
 * Vacation service implementation
 */
@Service
public class VacationService extends JpaService<Vacation, Long, IVacationRepository> implements IVacationService 
{
    private final VacationDTOAssembler vacationDTOAssembler;
    private final PagedResourcesAssembler<Vacation> vacationPagedResourcesAssembler;
    private final IPharmacyAdminService pharmacyAdminService;
    private final IMailService mailService;

    @Autowired
    public VacationService(IVacationRepository repository, VacationDTOAssembler vacationDTOAssembler, PagedResourcesAssembler<Vacation> vacationPagedResourcesAssembler, IPharmacyAdminService pharmacyAdminService, IMailService mailService)
    {
        super(repository);
        this.vacationDTOAssembler = vacationDTOAssembler;
        this.vacationPagedResourcesAssembler = vacationPagedResourcesAssembler;
        this.pharmacyAdminService = pharmacyAdminService;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public Optional<VacationDTO> getDTO(Long id) 
    {
        return get(id).map(vacationDTOAssembler::toModel);
    }

    @Override
    @Transactional
    public PagedModel<VacationDTO> getAllDTO(Pageable pageable) 
    {
        Page<Vacation> vacations = getAll(pageable);
        return vacationPagedResourcesAssembler.toModel(vacations, vacationDTOAssembler);
    }

    @Override
    @Transactional
    public PagedModel<VacationDTO> getCreatedVacationsByPharmacy(String email, Pageable pageable)
    {
        Page<Vacation> vacations = repository.findCreatedVacations(pharmacyAdminService.findByEmail(email).get().getPharmacy().getId(), pageable);
        return vacationPagedResourcesAssembler.toModel(vacations, vacationDTOAssembler);
    }

    @Override
    public boolean acceptVacation(Long id)
    {
        Vacation vacation = get(id).get();
        vacation.setVacationStatus(VacationStatus.ACCEPTED);
        if (update(vacation)) {
            mailService.sendVacationMedicalEmployee(vacation.getMedicalEmployee().getEmail(), "Your vacation from " + vacation.getPeriod().getStartTime().toLocalDate() + " to " + vacation.getPeriod().getEndTime().toLocalDate() + " is ACCEPTED!");
            return true;
        }
        return false;
    }

    @Override
    public boolean rejectVacation(VacationRequest vacationRequest)
    {
        Vacation vacation = get(vacationRequest.getId()).get();
        vacation.setVacationStatus(VacationStatus.REJECTED);
        vacation.setReasonOfRejection(vacationRequest.getReason());
        if(update(vacation)) {
            mailService.sendVacationMedicalEmployee(vacation.getMedicalEmployee().getEmail(), "Your vacation from " + vacation.getPeriod().getStartTime().toLocalDate() + " to " + vacation.getPeriod().getEndTime().toLocalDate() + " is REJECTED! Reason: " + vacation.getReasonOfRejection());
            return true;
        }
        return false;
    }

    @Override
    public List<Vacation> findByMedicalEmployeeId(Long id)
    {
        return repository.findByMedicalEmployeeId(id);
    }


}
