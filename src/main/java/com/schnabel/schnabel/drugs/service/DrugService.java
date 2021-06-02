package com.schnabel.schnabel.drugs.service;

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
}
