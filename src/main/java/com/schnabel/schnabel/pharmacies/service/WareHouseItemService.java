package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.repository.IWareHouseItemRepository;
import java.util.Optional;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTOAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Implementation of WareHouseItem service
 */
@Service
public class WareHouseItemService extends JpaService<WareHouseItem, Long, IWareHouseItemRepository> implements IWareHouseItemService
{
    private final WareHouseItemDTOAssembler dtoAssembler;
    private final PagedResourcesAssembler<WareHouseItem> pageAsm;

    @Autowired
    public WareHouseItemService(IWareHouseItemRepository repository, WareHouseItemDTOAssembler dtoAssembler, PagedResourcesAssembler<WareHouseItem> pageAsm)
    {
        super(repository);
        this.dtoAssembler = dtoAssembler;
        this.pageAsm = pageAsm;
    }

    @Override
    public Optional<WareHouseItem> findWareHouseItemByPharmacyAndDrugId(Long drugId, Long pharmacyId) {
        return Optional.empty();
    }

    @Override
    public Optional<WareHouseItemDTO> findByIdDTO(Long id) {
        return get(id).map(dtoAssembler::toModel);
    }
}
