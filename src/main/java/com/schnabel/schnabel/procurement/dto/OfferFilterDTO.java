package com.schnabel.schnabel.procurement.dto;

import com.schnabel.schnabel.procurement.model.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OfferFilterDTO {
    private OfferStatus offerStatus;
}
