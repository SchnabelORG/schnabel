package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.drugs.dto.DrugPriceRequest;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.pharmacies.model.Pharmacy;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.users.dto.DrugReservationRequest;
import com.schnabel.schnabel.users.model.Patient;

import java.util.List;
import java.util.Optional;

import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemRequest;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemUpdateRequest;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * WareHouseItem service interface
 */
public interface IWareHouseItemService extends IJpaService<WareHouseItem, Long>
{
    Optional<WareHouseItemDTO> findByIdDTO(Long id);   
    PagedModel<WareHouseItemDTO> findAllByPharmacyId(Long pharmacyId, Pageable pageable);
    Optional<WareHouseItem> findWareHouseItemByPharmacyAndDrugId(Long drugId, Long pharmacyId);
    Integer getStock(Long pharmacyId, Long drugId);
    boolean reserveDrug(DrugReservationRequest req, Patient patient);
    boolean deleteWareHouseItem(Long id);
    boolean updateWareHouseItem(WareHouseItemUpdateRequest wareHouseItemUpdateRequest);
    boolean addWareHouseItem(WareHouseItemRequest wareHouseItemRequest);
    boolean addDrugPrice(DrugPriceRequest drugPriceRequest);
}
