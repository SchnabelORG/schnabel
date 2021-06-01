package com.schnabel.schnabel.pharmacies.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.schnabel.schnabel.drugs.dto.DrugDTO;
import com.schnabel.schnabel.drugs.dto.DrugPriceDTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("warehouseitem")
@Relation(collectionRelation = "warehouseitems")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class WareHouseItemDTO extends RepresentationModel<WareHouseItemDTO>{
    private Long id;
    private int quantity;
    private int available;
    //private DrugPriceDTO drugPrice;
    private DrugDTO drug;
    private PharmacyDTO pharmacy;
}
