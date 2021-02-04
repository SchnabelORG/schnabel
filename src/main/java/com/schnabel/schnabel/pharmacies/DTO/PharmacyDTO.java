package com.schnabel.schnabel.pharmacies.DTO;

import com.schnabel.schnabel.misc.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyDTO {
    private String name;
    private Address address;
}
