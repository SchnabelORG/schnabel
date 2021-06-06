package com.schnabel.schnabel.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.service.IPharmacyService;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.DermatologistDTOAssembler;
import com.schnabel.schnabel.users.model.Dermatologist;
import com.schnabel.schnabel.users.repository.DermatologistSpecification;
import com.schnabel.schnabel.users.repository.IDermatologistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

/**
 * Dermatologist service implementation
 */
@Service
public class DermatologistService extends JpaService<Dermatologist, Long, IDermatologistRepository> implements IDermatologistService
{
    private final DermatologistDTOAssembler dermatologistDTOAsm;
    private final PagedResourcesAssembler<Dermatologist> dermatologistPagedAsm;
    private final IPharmacyService pharmacyService;

    @Autowired
    public DermatologistService(IDermatologistRepository repository, PagedResourcesAssembler<Dermatologist> dermatologistPagedAsm, DermatologistDTOAssembler dermatologistDTOAsm, IPharmacyService pharmacyService)
    {
		  super(repository);
          this.dermatologistPagedAsm = dermatologistPagedAsm;
          this.dermatologistDTOAsm = dermatologistDTOAsm;
          this.pharmacyService = pharmacyService;
	  }

    @Override
    @Transactional
    public Optional<DermatologistDTO> getDTO(Long id) {
        return get(id)
        .map(dermatologistDTOAsm::toModel);
    }

    @Override
    @Transactional
    public PagedModel<DermatologistDTO> getAllDTO(Pageable pageable) {
        Page<Dermatologist> dermatologists = getAll(pageable);
        return dermatologistPagedAsm.toModel(dermatologists, dermatologistDTOAsm);
    }

    @Override
    public Optional<Dermatologist> findByEmail(String email)
    {
        return repository.findByEmail(email);
    }

    @Override
    @Transactional
    public PagedModel<DermatologistDTO> findAllByPharmacy(Long pharmacyId, Pageable pageable) {
        Optional<Pharmacy> pharmacy = pharmacyService.get(pharmacyId);
        if(!pharmacy.isPresent()) {
            return null;
        }
        Page<Dermatologist> dermatologists = repository.findAllDermatologistsPharmacy(pharmacyId, pageable);
        return dermatologistPagedAsm.toModel(dermatologists, dermatologistDTOAsm);
    }

    @Override
    @Transactional
    public PagedModel<DermatologistDTO> filteredSearch(Map<String, String> params, Pageable pageable) {
        Page<Dermatologist> dermatologists = repository.findAll(DermatologistSpecification.filteredQuery(params), pageable);
        return dermatologistPagedAsm.toModel(dermatologists, dermatologistDTOAsm);
    }

    @Override
    @Transactional
    public PagedModel<DermatologistDTO> filteredSearchPharmacyAdmin(Map<String, String> params, Long pharmacyId, Pageable pageable) 
    {
        List<Dermatologist> derms = new ArrayList<Dermatologist>();
        List<Dermatologist> dermAll = repository.findAll(DermatologistSpecification.filteredQuery(params), pageable).getContent();
        List<Dermatologist> dermPharmacy = repository.findAllDermatologistsPharmacy(pharmacyId, pageable).getContent();

        for (Dermatologist dermatologist : dermAll) {
            if(dermPharmacy.contains(dermatologist)) {
                derms.add(dermatologist);
            }
        }
        Page<Dermatologist> dermatologists = new PageImpl<>(derms);
        return dermatologistPagedAsm.toModel(dermatologists, dermatologistDTOAsm);
    }

    @Override
    @Transactional
    public PagedModel<DermatologistDTO> findAllDermatologistNotInPharmacy(Long pharmacyId, Pageable pageable)
    {
        List<Dermatologist> retVal = new ArrayList<Dermatologist>();
        List<Dermatologist> dermatologists = repository.findAllDermatologistNotInPharmacy(pharmacyId, pageable).getContent();
        List<Dermatologist> derms = repository.findAllDermatologistsPharmacy(pharmacyId, pageable).getContent();

        for (Dermatologist dermatologist : dermatologists) {
            if(!derms.contains(dermatologist)) {
                retVal.add(dermatologist);
            }
        }
        Page<Dermatologist> page = new PageImpl<>(retVal);
        return dermatologistPagedAsm.toModel(page, dermatologistDTOAsm);
    }
}


