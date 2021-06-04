package com.schnabel.schnabel.promotion.dto;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.schnabel.schnabel.promotion.controller.PromotionController;
import com.schnabel.schnabel.promotion.model.Promotion;

/**
 * Assembles Promotion
 */
@Component
public class PromotionDTOAssembler extends RepresentationModelAssemblerSupport<Promotion, PromotionDTO> {
    
    public PromotionDTOAssembler() {
        super(PromotionController.class, PromotionDTO.class);
    }

    @Override
    public PromotionDTO toModel(Promotion entity) {
        PromotionDTO dto = instantiateModel(entity);

        dto.add(linkTo(methodOn(PromotionController.class).get(entity.getId())).withSelfRel());
        dto.setDescription(entity.getDescription());
        dto.setPeriod(entity.getPeriod());

        return dto;
    }
}
