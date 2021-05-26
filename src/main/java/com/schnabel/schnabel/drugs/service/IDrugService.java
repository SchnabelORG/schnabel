package com.schnabel.schnabel.drugs.service;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.enums.DrugOrigin;
import com.schnabel.schnabel.drugs.model.enums.DrugState;
import com.schnabel.schnabel.drugs.model.enums.DrugType;
import com.schnabel.schnabel.drugs.model.enums.IssuingType;
import com.schnabel.schnabel.misc.interfaces.IJpaService;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

public interface IDrugService extends IJpaService<Drug, Long> {
    Optional<DrugDTO> getDTO(Long id);
    PagedModel<DrugDTO> getAllDTO(Pageable pageable);
    boolean registerDrug(String code, String name, String description, DrugState drugState, DrugOrigin drugOrigin, DrugType drugType, String producer, String dosage, IssuingType issuingType);
}
