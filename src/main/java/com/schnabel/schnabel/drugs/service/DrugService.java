package com.schnabel.schnabel.drugs.service;

import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.dto.DrugDTOAssembler;
import com.schnabel.schnabel.drugs.model.Drug;
import com.schnabel.schnabel.drugs.model.Drug_;
import com.schnabel.schnabel.drugs.model.enums.DrugOrigin;
import com.schnabel.schnabel.drugs.model.enums.DrugState;
import com.schnabel.schnabel.drugs.model.enums.DrugType;
import com.schnabel.schnabel.drugs.model.enums.IssuingType;
import com.schnabel.schnabel.drugs.repository.IDrugRepository;
import com.schnabel.schnabel.misc.implementations.JpaService;

import java.util.*;
import java.util.stream.Collectors;

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

import javax.transaction.Transactional;

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

    /*@Override
    public PagedModel<DrugDTO> filteredSearch(Map<String, String> params, Pageable pageable) {
        Page<Drug> drugs = repository.findAll(DrugSpecification.filteredQuery(params), pageable);
        return pageAsm.toModel(drugs, dtoAsm);
    }

    if(params.containsKey("name")) {
        predicates.add(cb.like(cb.lower(root.get(Drug_.NAME)), "%" + params.get("name").toLowerCase() + "%"));
    } else if(params.containsKey("type") && params.containsKey("name")) {
        predicates.add(cb.like(cb.lower(root.get(Drug_.DRUG_TYPE)), "%" + DrugType.convert(params.get("type")) + "%"));
        predicates.add(cb.like(cb.lower(root.get(Drug_.NAME)), "%" + params.get("name").toLowerCase() + "%"));
    } else if(params.containsKey("type") && !params.containsKey("name")) {
        predicates.add(cb.like(cb.lower(root.get(Drug_.DRUG_TYPE)),DrugType.convert(params.get("type"))));
    } else if(params.containsKey("type") && params.containsKey("name") && params.containsKey("score")){
        predicates.add(cb.like(cb.lower(root.get(Drug_.DRUG_TYPE)), "%" + params.get("type") + "%"));
        predicates.add(cb.like(cb.lower(root.get(Drug_.NAME)), "%" + params.get("name").toLowerCase() + "%"));
        predicates.add(cb.like(cb.lower(root.get(Drug_.SCORE)), "%" + params.get("name").toLowerCase() + "%"));
    } else if(params.containsKey("type") && !params.containsKey("name") && params.containsKey("score")){
        predicates.add(cb.like(cb.lower(root.get(Drug_.DRUG_TYPE)), "%" + params.get("type") + "%"));
        predicates.add(cb.like(cb.lower(root.get(Drug_.SCORE)), "%" + params.get("name").toLowerCase() + "%"));
    } else if(!params.containsKey("type") && params.containsKey("name") && params.containsKey("score")){
        predicates.add(cb.like(cb.lower(root.get(Drug_.NAME)), "%" + params.get("name").toLowerCase() + "%"));
        predicates.add(cb.like(cb.lower(root.get(Drug_.SCORE)), "%" + params.get("name").toLowerCase() + "%"));
    } else if(!params.containsKey("type") && !params.containsKey("name") && params.containsKey("score")){
        predicates.add(cb.like(cb.lower(root.get(Drug_.SCORE)), "%" + params.get("name").toLowerCase() + "%"));
    }

    */

    @Override
    public PagedModel<DrugDTO> filteredSearch(Map<String, String> params, Pageable pageable) {
        Page<Drug> drugs;
        if(params.containsKey("name")) {
            drugs = repository.findByNameLike(params.get("name"), pageable);
        } else if(params.containsKey("type") && params.containsKey("name")) {
            drugs = repository.findByNameLikeAndDrugType(params.get("name"), DrugType.convert(params.get("type")), pageable);
        } else if(params.containsKey("type") && !params.containsKey("name")) {
            drugs = repository.findByDrugType(DrugType.convert(params.get("type")), pageable);
        } else if(params.containsKey("type") && params.containsKey("name") && params.containsKey("score")){
            drugs = repository.findByNameLikeAndDrugTypeAndScoreGreaterThanEqual(params.get("name"), DrugType.convert(params.get("type")), Double.parseDouble(params.get("score")), pageable);
        } else if(params.containsKey("type") && !params.containsKey("name") && params.containsKey("score")){
            drugs = repository.findByDrugTypeAndScoreGreaterThanEqual(DrugType.convert(params.get("type")), Double.parseDouble(params.get("score")), pageable);
        } else if(!params.containsKey("type") && params.containsKey("name") && params.containsKey("score")){
            drugs = repository.findByNameLikeAndScoreGreaterThanEqual(params.get("name"), Double.parseDouble(params.get("score")), pageable);
        } else if(!params.containsKey("type") && !params.containsKey("name") && params.containsKey("score")) {
            drugs = repository.findByScoreGreaterThanEqual(Double.parseDouble(params.get("score")), pageable);
        } else {
            drugs = repository.findAll(pageable);
        }
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
    public boolean registerDrug(String code, String name, String description, DrugState drugState, DrugOrigin drugOrigin, DrugType drugType, String producer, String dosage, IssuingType issuingType, List<Long> substituteDrugs) {
        Drug newDrug = new Drug(code, name,  description, drugType, drugState, drugOrigin , producer, dosage, issuingType);
        for(Long id : substituteDrugs){
            Drug d = repository.findById(id).get();
            newDrug.getSubstituteDrugs().add(d);
        }
        Optional<Drug> drug = add(newDrug);
        if(drug.isPresent())
            return true;
        return false;
    }

    @Override
    public boolean updateDrug(Long id, String code, String name, String description, DrugState drugState, DrugOrigin drugOrigin, DrugType drugType, String producer, String dosage, IssuingType issuingType, List<Long> substituteDrugs) {
        Optional<Drug> drug = get(id);
        if(!drug.isPresent()) {
            return false;
        }
        drug.get().setCode(code);
        drug.get().setName(name);
        drug.get().setDescription(description);
        drug.get().setDrugState(drugState);
        drug.get().setDrugOrigin(drugOrigin);
        drug.get().setDrugType(drugType);
        drug.get().setDosage(dosage);
        drug.get().setProducer(producer);
        drug.get().setIssuingType(issuingType);
        drug.get().setSubstituteDrugs(repository.findAllById(substituteDrugs));
        return update(drug.get());
    }

    @Override
    public Enum<DrugType>[] getDrugTypes() {
        return DrugType.values();
    }

    @Override
    public Enum<DrugOrigin>[] getDrugOrigin() {
        return DrugOrigin.values();
    }

    @Override
    public Enum<DrugState>[] getDrugState() {
        return DrugState.values();
    }

    @Override
    @Transactional
    public List<DrugDTO> getSubstitute(Long id) {
        return  get(id).get().getSubstituteDrugs().
                stream().
                map(dtoAsm::toModel).collect(Collectors.toList());
    }


}
