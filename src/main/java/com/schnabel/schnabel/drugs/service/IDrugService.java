package com.schnabel.schnabel.drugs.service;

import java.util.Map;
import java.util.Optional;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import com.schnabel.schnabel.users.dto.PharmacistDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

/**
 * Drug JPA service interface
 */
public interface IDrugService extends IJpaService<Drug, Long> 
{
    Optional<DrugDTO> findByIdDTO(Long id);
    PagedModel<DrugDTO> findAllDTO(Pageable pageable);
    PagedModel<DrugDTO> filteredSearch(Map<String, String> params, Pageable pageable);
    PagedModel<DrugDTO> findGradeable(Long patientId, Pageable pageable);
}
