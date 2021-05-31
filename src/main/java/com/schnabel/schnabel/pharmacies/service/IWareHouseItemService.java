package com.schnabel.schnabel.pharmacies.service;

import java.util.Optional;

import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;
import com.schnabel.schnabel.pharmacies.model.WareHouseItem;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * WareHouseItem service interface
 */
public interface IWareHouseItemService extends IJpaService<WareHouseItem, Long>
{
    Optional<WareHouseItemDTO> findByIdDTO(Long id);
    PagedModel<WareHouseItemDTO> findByPharmacyId(Long pharmacyId, Pageable pageable);   
}
