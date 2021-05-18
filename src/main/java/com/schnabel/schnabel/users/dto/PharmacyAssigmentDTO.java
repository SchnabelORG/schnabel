package com.schnabel.schnabel.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PharmacyAssigmentDTO {
    private long pharmacyAdminId;
    private long pharmacyId;
}
