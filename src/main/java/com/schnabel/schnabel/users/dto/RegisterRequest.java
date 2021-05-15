package com.schnabel.schnabel.users.dto;

import com.schnabel.schnabel.misc.model.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RegisterRequest {
    private String fName;
    private String lName;
    private String password;
    private String phoneNo;
    private String email;
    private Address address;
}
