package com.schnabel.schnabel.users.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.service.IDrugService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.security.util.JwtUtils;
import com.schnabel.schnabel.users.dto.AllergyDTO;
import com.schnabel.schnabel.users.dto.AllergyDTOAssembler;
import com.schnabel.schnabel.users.model.Allergy;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.users.repository.IAllergyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class AllergyService extends JpaService<Allergy, Long, IAllergyRepository> implements IAllergyService {

    private final AllergyDTOAssembler dtoAsm;
    private final PagedResourcesAssembler<Allergy> pageAsm;
    private final JwtUtils jwtUtils;
    private final IPatientService patientService;
    private final IDrugService drugService;

    @Autowired
    public AllergyService(IAllergyRepository repository, AllergyDTOAssembler dtoAsm, PagedResourcesAssembler<Allergy> pageAsm, JwtUtils jwtUtils, IPatientService patientService, IDrugService drugService) {
        super(repository);
        this.dtoAsm = dtoAsm;
        this.pageAsm = pageAsm;
        this.jwtUtils = jwtUtils;
        this.patientService = patientService;
        this.drugService = drugService;
    }

    @Override
    public PagedModel<AllergyDTO> findAllDTO(Pageable pageable) {
        Page<Allergy> allergies = getAll(pageable);
        return pageAsm.toModel(allergies, dtoAsm);
    }

    @Override
    @Transactional
    public Optional<AllergyDTO> findByIdDTO(Long id) {
        return get(id).map(dtoAsm::toModel);
    }

    @Override
    @Transactional
    public PagedModel<AllergyDTO> findByPatientId(String authHeader, Pageable pageable) {
        String email = jwtUtils.getEmailFromAuth(authHeader);
        Optional<Patient> patient = patientService.findByEmail(email);
        if(!patient.isPresent()) {
            return PagedModel.empty();
        }
        Page<Allergy> allergies = repository.findByPatientId(patient.get().getId(), pageable);
        return pageAsm.toModel(allergies, dtoAsm);
    }

    @Override
    @Transactional
    public boolean addToPatient(String authHeader, Long drugId) {
        String email = jwtUtils.getEmailFromAuth(authHeader);
        Optional<Patient> patient = patientService.findByEmail(email);
        Optional<Drug> drug = drugService.get(drugId);
        if(!patient.isPresent() || !drug.isPresent()) {
            return false;
        }
        if(repository.existsByPatientIdAndDrugId(patient.get().getId(), drugId)) {
            return true;
        }

        Allergy newAllergy = new Allergy(drug.get(), patient.get());
        add(newAllergy);
        return true;
    }

    @Override
    @Transactional
    public List<String> findPotentialAllergies() {
        return StreamSupport.stream(drugService.getAll().spliterator(), false)
            .map(d -> d.getName())
            .collect(Collectors.toList());
    }
    
}
