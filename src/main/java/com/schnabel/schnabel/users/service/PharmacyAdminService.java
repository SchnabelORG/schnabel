package com.schnabel.schnabel.users.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.repository.IPharmacyRepository;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTO;
import com.schnabel.schnabel.users.dto.PharmacyAdminDTOAssembler;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.repository.IPharmacyAdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PharmacyAdminService extends JpaService<PharmacyAdmin, Long, IPharmacyAdminRepository> implements IPharmacyAdminService
{
    private final PharmacyAdminDTOAssembler pharmacyAdminDTOAssembler;
    private final PagedResourcesAssembler<PharmacyAdmin> pharmacyAdminDtoPagedResourcesAssembler;
    private final IPharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyAdminService(IPharmacyAdminRepository pharmacyAdminRepository, PharmacyAdminDTOAssembler pharmacyAdminDTOAssembler, PagedResourcesAssembler<PharmacyAdmin> pharmacyAdminDtoPagedResourcesAssembler, IPharmacyRepository pharmacyRepository)
    {
        super(pharmacyAdminRepository);
        this.pharmacyAdminDTOAssembler = pharmacyAdminDTOAssembler;
        this.pharmacyAdminDtoPagedResourcesAssembler = pharmacyAdminDtoPagedResourcesAssembler;
        this.pharmacyRepository = pharmacyRepository;
    }

    @Override
    public Optional<PharmacyAdminDTO> getDTO(Long id) {
        return get(id).map(pharmacyAdminDTOAssembler::toModel);
    }

    @Override
    public PagedModel<PharmacyAdminDTO> getAllDTO(Pageable pageable) {
        Page<PharmacyAdmin> pharmacyAdmins = getAll(pageable);
        return pharmacyAdminDtoPagedResourcesAssembler.toModel(pharmacyAdmins, pharmacyAdminDTOAssembler);
    }

    @Override
    public PagedModel<PharmacyAdminDTO> findByPharmacy(Pageable pageable, Long pharmacyId){
        Page<PharmacyAdmin> pharmacyAdmins =  repository.findByPharmacyId(pageable ,pharmacyId);
        return pharmacyAdminDtoPagedResourcesAssembler.toModel(pharmacyAdmins, pharmacyAdminDTOAssembler);
    }

    @Override
    public PagedModel<PharmacyAdminDTO> findWithoutPharmacy(Pageable pageable){
        Page<PharmacyAdmin> pharmacyAdmins = repository.findByPharmacyId(pageable, null);
        return pharmacyAdminDtoPagedResourcesAssembler.toModel(pharmacyAdmins, pharmacyAdminDTOAssembler);
    }

    @Override
    public boolean assignPharmacyAdmin(Long pharmacyAdminId, Long pharmacyId) {
        try {
            PharmacyAdmin padmin = repository.findById(pharmacyAdminId).get();
            Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).get();
            if (padmin != null && pharmacy != null) {
                padmin.setPharmacy(pharmacy);
                return update(padmin);
            }
            return false;
        } catch (Exception e)
        {
            return false;
        }
    }

}
