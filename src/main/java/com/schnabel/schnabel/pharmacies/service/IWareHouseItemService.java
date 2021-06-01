package com.schnabel.schnabel.pharmacies.service;

import java.util.Optional;

import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * WareHouseItem service interface
 */
public interface IWareHouseItemService 
{
    Optional<WareHouseItemDTO> findByIdDTO(Long id);   
    PagedModel<WareHouseItemDTO> findAllByPharmacyId(Long pharmacyId, Pageable pageable);
}
