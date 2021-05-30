package com.schnabel.schnabel.drugs.dto;


import com.schnabel.schnabel.drugs.controller.DrugController;
import com.schnabel.schnabel.drugs.model.Drug;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DrugDTOAssembler extends RepresentationModelAssemblerSupport<Drug, DrugDTO> {

    public DrugDTOAssembler()
    {
        super(DrugController.class, DrugDTO.class);
    }


    @Override
    public DrugDTO toModel(Drug entity) {
        DrugDTO dto = instantiateModel(entity);

        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setDrugState(entity.getDrugState());
        dto.setDrugType(entity.getDrugType());
        dto.setDrugOrigin(entity.getDrugOrigin());
        dto.setProducer(entity.getProducer());
        dto.setDosage(entity.getDosage());
        dto.setIngredients(entity.getIngredients());
        dto.setIssuingType(entity.getIssuingType());
        dto.setSubstituteDrugs(drugSubstituteDTOList(entity.getSubstituteDrugs()));
        return dto;
    }


    private List<DrugSubstituteDTO> drugSubstituteDTOList(List<Drug> drugs)
    {
        if (drugs.isEmpty()) {
            return Collections.emptyList();
        }

        return drugs.stream()
                .map(d -> DrugSubstituteDTO.builder()
                .id(d.getId())
                .name(d.getName())
                .drugType(d.getDrugType())
                .producer(d.getProducer())
                .dosage(d.getDosage())
                .build()).collect(Collectors.toList());
    }
}
