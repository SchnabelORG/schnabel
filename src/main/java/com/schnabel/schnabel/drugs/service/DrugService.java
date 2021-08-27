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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.dto.DrugDTOAssembler;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.repository.DrugSpecification;
import com.schnabel.schnabel.drugs.repository.IDrugRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

/**
 * Drug JPA service implementation
 */
@Service
public class DrugService extends JpaService<Drug, Long, IDrugRepository> implements IDrugService {
    
    private final DrugDTOAssembler dtoAsm;
    private final PagedResourcesAssembler<Drug> pageAsm;

    @Autowired
    public DrugService(IDrugRepository repository, DrugDTOAssembler dtoAsm, PagedResourcesAssembler<Drug> pageAsm)
    {
        super(repository);
        this.dtoAsm = dtoAsm;
        this.pageAsm = pageAsm;
    }

    @Override
    public Optional<DrugDTO> findByIdDTO(Long id) {
        return get(id).map(dtoAsm::toModel);
    }

    @Override
    public PagedModel<DrugDTO> filteredSearch(Map<String, String> params, Pageable pageable) {
        Page<Drug> drugs = repository.findAll(DrugSpecification.filteredQuery(params), pageable);
        return pageAsm.toModel(drugs, dtoAsm);
    }

    @Override
    public PagedModel<DrugDTO> findAllDTO(Pageable pageable) {
        Page<Drug> drugs = getAll(pageable);
        return pageAsm.toModel(drugs, dtoAsm);
    }
    
    @Override
    public PagedModel<DrugDTO> findGradeable(Long patientId, Pageable pageable) {
        Page<Drug> drugs = repository.findGradeable(patientId, pageable);
        return pageAsm.toModel(drugs, dtoAsm);
    }

    @Override
    public Optional<Drug> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<DrugDTO> findAllDTO() {
        return dtoAsm.listModel(getAll());
    }


    @Override
    public boolean registerDrug(String code, String name, String description, DrugState drugState, DrugOrigin drugOrigin, DrugType drugType, String producer, String dosage, IssuingType issuingType) {
        Drug newDrug = new Drug(code, name,  description, drugType, drugState, drugOrigin , producer, dosage, issuingType);
        Optional<Drug> drug = add(newDrug);
        if(drug.isPresent())
            return true;
        return false;
    }
}
