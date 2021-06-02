package com.schnabel.schnabel.users.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.service.IWareHouseItemService;
import com.schnabel.schnabel.users.dto.DermatologistDTO;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import com.schnabel.schnabel.users.model.PharmacyAdmin;
import com.schnabel.schnabel.users.repository.IPharmacyAdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class PharmacyAdminService extends JpaService<PharmacyAdmin, Long, IPharmacyAdminRepository> implements IPharmacyAdminService
{
    private final IDermatologistService dermatologistService;
    private final IPharmacistService pharmacistService;
    private final IWareHouseItemService wareHouseItemService;

    @Autowired
    public PharmacyAdminService(IPharmacyAdminRepository pharmacyAdminRepository, IDermatologistService dermatologistService, IPharmacistService pharmacistService, IWareHouseItemService wareHouseItemService)
    {
        super(pharmacyAdminRepository);
        this.dermatologistService = dermatologistService;
        this.pharmacistService = pharmacistService;
        this.wareHouseItemService = wareHouseItemService;
    }

    @Override
    public Optional<PharmacyAdmin> findByEmail(String email)
    {
        return repository.findByEmail(email);
    }

    @Override
    public PagedModel<DermatologistDTO> getAllDermatologists(String email, Pageable pageable)
    {
        return dermatologistService.findAllByPharmacy(findByEmail(email).get().getPharmacy().getId(), pageable);
    }

    @Override
    public PagedModel<PharmacistDTO> getAllPharmacists(String email, Pageable pageable)
    {
        return pharmacistService.findByPharmacy(findByEmail(email).get().getPharmacy().getId(), pageable);
    }

    @Override
    public PagedModel<WareHouseItemDTO> getAllDrugs(String email, Pageable pageable)
    {
        return wareHouseItemService.findAllByPharmacyId(findByEmail(email).get().getPharmacy().getId(), pageable);
    }

}