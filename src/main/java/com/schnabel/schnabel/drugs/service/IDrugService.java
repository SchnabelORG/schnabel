package com.schnabel.schnabel.drugs.service;

import java.util.Optional;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.misc.interfaces.IJpaService;

/**
 * Drug JPA service interface
 */
public interface IDrugService extends IJpaService<Drug, Long> 
{
    Optional<DrugDTO> findByIdDTO(Long id);
}
