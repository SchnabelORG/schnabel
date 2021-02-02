package com.schnabel.schnabel.pharmacies.dto;

import com.schnabel.schnabel.misc.model.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class used for pharmacy search
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacySearchDTO
{
    private String name;
    private Address address; 
}