package com.schnabel.schnabel.pharmacies.service;

import java.util.Optional;

import com.schnabel.schnabel.pharmacies.dto.WareHouseItemDTO;

/**
 * WareHouseItem service interface
 */
public interface IWareHouseItemService 
{
    Optional<WareHouseItemDTO> findByIdDTO(Long id);   
}
