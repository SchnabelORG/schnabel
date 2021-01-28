package com.schnabel.schnabel.drugs.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UrgentProcurementPequest
{
    private String medicationName;
    private String medicationDosage;
    private int medicationQuantity;
}
