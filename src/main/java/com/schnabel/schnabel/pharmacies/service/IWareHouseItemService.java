package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;

import java.util.Optional;


import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;

/**
 * WareHouseItem service interface
 */
public interface IWareHouseItemService extends IJpaService<WareHouseItem, Long> {
    Optional<WareHouseItem> findWareHouseItemByPharmacyAndDrugId(Long drugId, Long pharmacyId);
    Optional<WareHouseItemDTO> findByIdDTO(Long id);
}
