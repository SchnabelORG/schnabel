package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.dto.PharmacyCreationDTO;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTO;
import com.schnabel.schnabel.pharmacies.dto.PharmacyDTOAssembler;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PharmacyService extends JpaService<Pharmacy, Long, IPharmacyRepository> implements IPharmacyService{

    private final PharmacyDTOAssembler pharmacyDTOAssembler;
    private final PagedResourcesAssembler<Pharmacy> pharmacyPagedResourcesAssembler;


    @Autowired
    public PharmacyService(IPharmacyRepository repository, PharmacyDTOAssembler pharmacyDTOAssembler, PagedResourcesAssembler<Pharmacy> pharmacyPagedResourcesAssembler) {
        super(repository);
        this.pharmacyDTOAssembler = pharmacyDTOAssembler;
        this.pharmacyPagedResourcesAssembler = pharmacyPagedResourcesAssembler;
    }

    @Override
    public boolean registerPharmacy(PharmacyCreationDTO creationDTO) {
        Pharmacy newPharmacy = new Pharmacy(creationDTO.getName(), creationDTO.getAddress());
        Optional<Pharmacy> pharmacy = add(newPharmacy);
        return pharmacy.isPresent();
    }

    @Override
    public Optional<PharmacyDTO> getDTO(Long id) {
        return get(id).map(pharmacyDTOAssembler::toModel);
    }

    @Override
    public PagedModel<PharmacyDTO> getAllDTO(Pageable pageable) {
        Page<Pharmacy> pharmacies = getAll(pageable);
        return pharmacyPagedResourcesAssembler.toModel(pharmacies, pharmacyDTOAssembler);
    }
}
