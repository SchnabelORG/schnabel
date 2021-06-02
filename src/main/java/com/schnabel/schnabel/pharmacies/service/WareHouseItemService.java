package com.schnabel.schnabel.pharmacies.service;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.repository.IWareHouseItemRepository;
import com.schnabel.schnabel.users.dto.DrugReservationRequest;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTOAssembler;

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
    public Optional<WareHouseItem> findWareHouseItemByPharmacyAndDrugId(Long drugId, Long pharmacyId) {
        Optional<WareHouseItem> wareHouseItem = repository.findByPharmacyIdAndDrugId(drugId, pharmacyId);
        return wareHouseItem;
    }

    @Override
    public Optional<WareHouseItemDTO> findByIdDTO(Long id) {
        return get(id).map(dtoAssembler::toModel);
    }

    @Override
    @Transactional
    public PagedModel<WareHouseItemDTO> findAllByPharmacyId(Long pharmacyId, Pageable pageable)
    {
        try {
            Page<WareHouseItem> warehouseitems = repository.findByPharmacyId(pharmacyId, pageable);
            return pageAsm.toModel(warehouseitems, dtoAssembler);
        } catch (NoResultException ignore) {
            return PagedModel.empty();
        }
    }

    @Override
    public Integer getStock(Long pharmacyId, Long drugId) {
        Optional<WareHouseItem> warehouseItem = findWareHouseItemByPharmacyAndDrugId(drugId, pharmacyId);
        return warehouseItem.isPresent() ?
            warehouseItem.get().getAvailable()
            : 0;
    }

    @Override
    public boolean reserveDrug(DrugReservationRequest req, Patient patient) {
        Optional<WareHouseItem> item = findWareHouseItemByPharmacyAndDrugId(req.getDrugId(), req.getPharmacyId());
        if(!item.isPresent()) {
            return false;
        }
        return item.get().reduceAvailable(req.getQuantity())
            && update(item.get());
    }
}
