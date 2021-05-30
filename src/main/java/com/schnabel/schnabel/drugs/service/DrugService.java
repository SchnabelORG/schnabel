package com.schnabel.schnabel.drugs.service;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.dto.DrugDTOAssembler;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.enums.DrugOrigin;
import com.schnabel.schnabel.drugs.model.enums.DrugState;
import com.schnabel.schnabel.drugs.model.enums.DrugType;
import com.schnabel.schnabel.drugs.model.enums.IssuingType;
import com.schnabel.schnabel.drugs.repository.IDrugRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DrugService extends JpaService<Drug, Long, IDrugRepository> implements IDrugService {

    private final DrugDTOAssembler drugDTOAssembler;
    private final PagedResourcesAssembler<Drug> drugPagedResourcesAssembler;

    @Autowired
    public DrugService(IDrugRepository repository, DrugDTOAssembler drugDTOAssembler, PagedResourcesAssembler<Drug> drugPagedResourcesAssembler) {
        super(repository);
        this.drugDTOAssembler = drugDTOAssembler;
        this.drugPagedResourcesAssembler = drugPagedResourcesAssembler;
    }

    @Override
    public Optional<DrugDTO> getDTO(Long id) {
        return get(id).map(drugDTOAssembler::toModel);
    }

    @Override
    public PagedModel<DrugDTO> getAllDTO(Pageable pageable) {
        Page<Drug> drugs = getAll(pageable);
        return drugPagedResourcesAssembler.toModel(drugs, drugDTOAssembler);
    }

    @Override
    public boolean registerDrug(String code, String name, String description, DrugState drugState, DrugOrigin drugOrigin, DrugType drugType, String producer, String dosage, String ingredients, IssuingType issuingType) {
        Drug newDrug = new Drug(code, name,  description, drugType, drugState, drugOrigin , producer, dosage, ingredients, issuingType);
        Optional<Drug> drug = add(newDrug);
        if(drug.isPresent())
            return true;
        return false;
    }

    /**
     * Override add to check for if exist by code
     * @param drug
     * @return ture if added successful, else false
     */
    @Override
    public Optional<Drug> add(Drug drug)
    {
        if(drug.getId() == null || !repository.existsById(drug.getId()) || !repository.existsByCode(drug.getCode()) )
        {
            repository.save(drug);
        }
        return get(drug.getId());
    }
}
