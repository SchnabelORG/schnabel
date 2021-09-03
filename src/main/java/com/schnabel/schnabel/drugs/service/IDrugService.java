package com.schnabel.schnabel.drugs.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.enums.DrugOrigin;
import com.schnabel.schnabel.drugs.model.enums.DrugState;
import com.schnabel.schnabel.drugs.model.enums.DrugType;
import com.schnabel.schnabel.drugs.model.enums.IssuingType;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
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
    Optional<Drug> findByName(String name);
    List<DrugDTO> findAllDTO();
    boolean registerDrug(String code, String name, String description, DrugState drugState, DrugOrigin drugOrigin, DrugType drugType, String producer, String dosage, IssuingType issuingType, List<Long> substituteDrugs, double points);
    boolean updateDrug(Long id, String code, String name, String description, DrugState drugState, DrugOrigin drugOrigin, DrugType drugType, String producer, String dosage, IssuingType issuingType, List<Long> substituteDrugs);
    Enum<DrugType>[] getDrugTypes();
    Enum<DrugOrigin>[] getDrugOrigin();
    Enum<DrugState>[] getDrugState();
    List<DrugDTO> getSubstitute(Long id);

}
