package com.schnabel.schnabel.pharmacies.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.schnabel.schnabel.drugs.dto.DrugPriceRequest;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.DrugPrice;
import com.schnabel.schnabel.drugs.model.DrugReservation;
import com.schnabel.schnabel.drugs.repository.IDrugReservationRepository;
import com.schnabel.schnabel.drugs.service.IDrugPriceService;
import com.schnabel.schnabel.drugs.service.IDrugReservationService;
import com.schnabel.schnabel.drugs.service.IDrugService;
import com.schnabel.schnabel.misc.implementations.JpaService;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.pharmacies.repository.IWareHouseItemRepository;
import com.schnabel.schnabel.users.dto.DrugReservationRequest;
import com.schnabel.schnabel.users.model.Patient;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTOAssembler;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemRequest;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemUpdateRequest;

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
    private final IDrugService drugService;
    private final IPharmacyService pharmacyService;
    private final IDrugPriceService drugPriceService;
    private final IDrugReservationRepository drugReservationRepository;

    @Autowired
    public WareHouseItemService(IWareHouseItemRepository repository, WareHouseItemDTOAssembler dtoAssembler, PagedResourcesAssembler<WareHouseItem> pageAsm, IDrugService drugService, IPharmacyService pharmacyService, IDrugPriceService drugPriceService, IDrugReservationRepository drugReservationRepository)
    {
        super(repository);
        this.dtoAssembler = dtoAssembler;
        this.pageAsm = pageAsm;
        this.drugService = drugService;
        this.pharmacyService = pharmacyService;
        this.drugPriceService = drugPriceService;
        this.drugReservationRepository = drugReservationRepository;
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

    @Override
    public boolean deleteWareHouseItem(Long id) 
    {
        List<DrugReservation> drugReservations = drugReservationRepository.findNotTakenByDrugAndPharmacy(get(id).get().getDrug().getId(), get(id).get().getPharmacy().getId());
        if(!drugReservations.isEmpty()) {
            return false;
        }
        List<DrugPrice> drugPrices = drugPriceService.findAllByWareHouseItemId(id);
        for (DrugPrice drugPrice : drugPrices) {
            drugPriceService.remove(drugPrice.getId());
        }
        return remove(id);
    }

    @Override
    public boolean updateWareHouseItem(WareHouseItemUpdateRequest wareHouseItemUpdateRequest)
    {
        Optional<WareHouseItem> wareHouseItem = get(wareHouseItemUpdateRequest.getId());
        if(!wareHouseItem.isPresent()) {
            return false;
        }
        Optional<Drug> drug = drugService.get(wareHouseItemUpdateRequest.getDrugId());
        if(!drug.isPresent()) {
            return false;
        }
        drug.get().setName(wareHouseItemUpdateRequest.getName());
        drug.get().setDescription(wareHouseItemUpdateRequest.getDescription());
        drugService.update(drug.get());
        return update(wareHouseItem.get()); 
    }

    @Override
    public boolean addWareHouseItem(WareHouseItemRequest wareHouseItemRequest)
    {
        Drug newDrug = new Drug(wareHouseItemRequest.getName(), wareHouseItemRequest.getDescription());
        Optional<Drug> drug = drugService.add(newDrug);
        if(!drug.isPresent()) {
            return false;
        }
        WareHouseItem newWareHouseItem = new WareHouseItem(drug.get(), pharmacyService.get(wareHouseItemRequest.getPharmacyId()).get());
        Optional<WareHouseItem> wareHouseItem = add(newWareHouseItem);
        return wareHouseItem.isPresent();
    }

    @Override
    public boolean addDrugPrice(DrugPriceRequest drugPriceRequest) 
    {
        List<DrugPrice> drugPrices = drugPriceService.findAllByWareHouseItemId(drugPriceRequest.getWareHouseItemId());
        for (DrugPrice drugPrice : drugPrices) {
            if(drugPrice.getPriceStartDate().isBefore(drugPriceRequest.getPriceEndDate()) && drugPriceRequest.getPriceStartDate().isBefore(drugPrice.getPriceEndDate())) {
                return false;
            }
        }
        DrugPrice newDrugPrice = new DrugPrice(drugPriceRequest.getPrice(), drugPriceRequest.getPriceStartDate(), drugPriceRequest.getPriceEndDate(), get(drugPriceRequest.getWareHouseItemId()).get());
        Optional<DrugPrice> drugPrice = drugPriceService.add(newDrugPrice);
        return drugPrice.isPresent();
    }

}
