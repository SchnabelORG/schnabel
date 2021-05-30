package com.schnabel.schnabel.pharmacies.service;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;
import com.schnabel.schnabel.users.model.Pharmacist;

import java.util.Optional;


/**
 * WareHouseItem service interface
 */
public interface IWareHouseItemService extends IJpaService<WareHouseItem, Long> {
    Optional<WareHouseItem> findWareHouseItemByPharmacyAndDrugId(Long drugId, Long pharmacyId);
}
