package com.schnabel.schnabel.pharmacies.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.repository.IDrugRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.dto.*;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;
import com.schnabel.schnabel.pharmacies.repository.PharmacySpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

/**
 * Implementation of pharmacy service
 */
@Service
public class PharmacyService extends JpaService<Pharmacy, Long, IPharmacyRepository> implements IPharmacyService
{

    private final PharmacyDTOAssembler dtoAsm;
    private final PagedResourcesAssembler<Pharmacy> pageAsm;
    private static final long CONSULT_DURATION_MINUTES = 15;
    private final IDrugRepository drugRepository;

    @Autowired
    public PharmacyService(IPharmacyRepository pharmacyRepository, PharmacyDTOAssembler pharmacyDTOasm, PagedResourcesAssembler<Pharmacy> pharmacyPageAsm, IDrugRepository drugRepository)
    {
		  super(pharmacyRepository);
          this.dtoAsm = pharmacyDTOasm;
          this.pageAsm = pharmacyPageAsm;
        this.drugRepository = drugRepository;
    }

    @Override
    public boolean registerPharmacy(PharmacyCreationDTO creationDTO) {
        Pharmacy newPharmacy = new Pharmacy(creationDTO.getName(), creationDTO.getAddress());
        Optional<Pharmacy> pharmacy = add(newPharmacy);
        return pharmacy.isPresent();
    }

    @Override
    @Transactional
    public Optional<PharmacyDTO> getDTO(Long id) {
        return get(id)
            .map(dtoAsm::toModel);
    }

    @Override
    @Transactional
    public PagedModel<PharmacyDTO> getAllDTO(Pageable pageable) {
        Page<Pharmacy> pharmacies = getAll(pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }

    @Override
    @Transactional
    public PagedModel<PharmacyDTO> filteredSearch(Map<String, String> params, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findAll(PharmacySpecification.filteredQuery(params), pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }

    @Override
    public Optional<Pharmacy> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public PagedModel<PharmacyDTO> findByFreePharmacistAppointment(LocalDateTime startTime, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findByFreePharmacistAppointment(startTime, startTime.plusMinutes(CONSULT_DURATION_MINUTES), pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }

    @Override
    public PagedModel<PharmacyDTO> findWithStock(Long drugId, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findWithStock(drugId, pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }

    @Override
    public PagedModel<PharmacyDTO> findGraded(Long patientId, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findGraded(patientId, pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }

    @Override
    public PagedModel<PharmacyDTO> findGradeable(Long patientId, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findGradeable(patientId, pageable);
        return pageAsm.toModel(pharmacies, dtoAsm);
    }

    @Override
    public PagedModel<PharmacyDTO> findForERecipe(ERecipeDTO dto, Pageable pageable) {
        List<Drug> drugs = new ArrayList<>();
        for(ERecipeDrugDTO d: dto.getDrugs()) {
            Optional<Drug> drug = drugRepository.findByName(d.getName());
            drugs.add(drug.get());
        }


        return null;
    }

}
