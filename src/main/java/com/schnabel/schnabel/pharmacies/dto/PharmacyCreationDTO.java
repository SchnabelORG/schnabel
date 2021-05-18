package com.schnabel.schnabel.pharmacies.dto;

import com.schnabel.schnabel.misc.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PharmacyCreationDTO {
    private String name;
    private Address address;

}
