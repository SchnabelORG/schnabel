package com.schnabel.schnabel.pharmacies.service;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTOAssembler;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.repository.IWareHouseItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

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
    public Optional<WareHouseItemDTO> findByIdDTO(Long id) {
        return get(id).map(dtoAssembler::toModel);
    }

    @Override
    public PagedModel<WareHouseItemDTO> findByPharmacyId(Long pharmacyId, Pageable pageable)
    {
        try {
            Page<WareHouseItem> warehouseitems = repository.findByPharmacyId(pharmacyId, pageable);
            return pageAsm.toModel(warehouseitems, dtoAssembler);
        } catch (NoResultException ignore) {
            return PagedModel.empty();
        }
    }
}
