package com.schnabel.schnabel.drugs.service;

import java.util.Optional;

import com.schnabel.schnabel.drugs.dto.DrugPriceDTO;
import com.schnabel.schnabel.drugs.model.DrugPrice;
import com.schnabel.schnabel.misc.interfaces.IJpaService;

/**
 * DrugPrice JPA service interface
 */
public interface IDrugPriceService extends IJpaService<DrugPrice, Long> 
{
    Optional<DrugPriceDTO> findByIdDTO(Long id);   
}
