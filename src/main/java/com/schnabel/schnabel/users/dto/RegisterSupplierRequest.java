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
public class RegisterSupplierRequest {
    private String name;
    private String surname;
    private String password;
    private String email;
    private Address address;
    private String company;
}
