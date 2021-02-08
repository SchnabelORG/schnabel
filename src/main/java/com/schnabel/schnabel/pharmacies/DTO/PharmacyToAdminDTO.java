package com.schnabel.schnabel.pharmacies.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PharmacyToAdminDTO
{
    private String pharmacyName;
    private String pharmacyAdminEmail;
}
