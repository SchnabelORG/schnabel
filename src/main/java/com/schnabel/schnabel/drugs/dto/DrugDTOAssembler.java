package com.schnabel.schnabel.drugs.dto;


import com.schnabel.schnabel.drugs.controller.DrugController;
import com.schnabel.schnabel.drugs.model.Drug;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

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

        return dto;
    }
}
