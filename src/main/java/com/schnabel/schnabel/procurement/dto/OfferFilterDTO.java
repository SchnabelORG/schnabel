package com.schnabel.schnabel.procurement.dto;

import com.schnabel.schnabel.procurement.model.OfferStatus;
import com.schnabel.schnabel.users.dto.SupplierDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OfferFilterDTO {
    private Long id;
    private Integer price;
    private LocalDate dateOfDelivery;
    private OfferStatus offerStatus;
    private boolean editable;
}
