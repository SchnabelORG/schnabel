package com.schnabel.schnabel.users.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PharmacistRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String postcode;
    private String city;
    private String street;
    private String streetNo;
    private LocalTime startTime;
    private LocalTime endTime;
}
