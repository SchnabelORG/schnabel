package com.schnabel.schnabel.pharmacies.service;

import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.dto.PharmacyCreationDTO;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTOAssembler;
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

    private final PharmacyDTOAssembler pharmacyDTOasm;
    private final PagedResourcesAssembler<Pharmacy> pharmacyPageAsm;

    @Autowired
    public PharmacyService(IPharmacyRepository pharmacyRepository, PharmacyDTOAssembler pharmacyDTOasm, PagedResourcesAssembler<Pharmacy> pharmacyPageAsm)
    {
		  super(pharmacyRepository);
          this.pharmacyDTOasm = pharmacyDTOasm;
          this.pharmacyPageAsm = pharmacyPageAsm;
    }

    @Override
    public boolean registerPharmacy(PharmacyCreationDTO creationDTO) {
        Pharmacy newPharmacy = new Pharmacy(creationDTO.getName(), creationDTO.getAddress());
        Optional<Pharmacy> pharmacy = add(newPharmacy);
        return pharmacy.isPresent();
    }

    @Override
    public Optional<PharmacyDTO> getDTO(Long id) {
        return get(id)
            .map(pharmacyDTOasm::toModel);
    }

    @Override
    public PagedModel<PharmacyDTO> getAllDTO(Pageable pageable) {
        Page<Pharmacy> pharmacies = getAll(pageable);
        return pharmacyPageAsm.toModel(pharmacies, pharmacyDTOasm);
    }

    @Override
    @Transactional
    public PagedModel<PharmacyDTO> filteredSearch(Map<String, String> params, Pageable pageable) {
        Page<Pharmacy> pharmacies = repository.findAll(PharmacySpecification.filteredQuery(params), pageable);
        return pharmacyPageAsm.toModel(pharmacies, pharmacyDTOasm);
    }

}
